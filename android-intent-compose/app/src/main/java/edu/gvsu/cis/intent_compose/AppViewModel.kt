package edu.gvsu.cis.intent_compose

import android.Manifest
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.BatteryManager
import android.provider.Telephony
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import java.io.File

class AppViewModel(val app: Application): AndroidViewModel(app) {
    private val _imageFileUri = MutableStateFlow<Uri?>(null)
    val imageFileUri = _imageFileUri.asStateFlow()

    private val _batteryLevel = MutableStateFlow(0)
    @OptIn(FlowPreview::class)
    val batteryLevel = _batteryLevel.asStateFlow().debounce(timeoutMillis = 1000)

    private val batteryChangeReceiver = object: BroadcastReceiver() {
        override fun onReceive(ctx: Context?, intent: Intent?) {
            val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            _batteryLevel.update { level ?: -1 }
        }
    }


    init {
        app.applicationContext.revokeSelfPermissionOnKill(Manifest.permission.CALL_PHONE)
        app.applicationContext.apply {
            registerReceiver(batteryChangeReceiver,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        }
    }

    fun setupImageUri(): Uri {
        val ctx = app.applicationContext
        val imagePath = File(ctx.filesDir, "images")
//        val imagePath = File(context.cacheDir, "demo_images")
        if (!imagePath.exists())
            imagePath.mkdirs()

        val tmpFile = File.createTempFile("cs357", ".jpg", imagePath)
        val imageFileUri = FileProvider.getUriForFile(
            ctx, ctx.packageName + ".provider",
            tmpFile
        )
        _imageFileUri.update {
            imageFileUri
        }
        return imageFileUri
    }

    fun removeImageFile() {
        _imageFileUri.value?.let {
            app.applicationContext.contentResolver.delete(it, null, null)
        }
    }

    override fun onCleared() {
        super.onCleared()
        app.applicationContext.apply {
            unregisterReceiver(batteryChangeReceiver)
        }
    }

}