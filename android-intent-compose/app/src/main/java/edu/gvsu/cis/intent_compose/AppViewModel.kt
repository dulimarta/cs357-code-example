package edu.gvsu.cis.intent_compose

import android.Manifest
import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File

class AppViewModel(val app: Application): AndroidViewModel(app) {
//    private val context: Context = app.applicationContext
    private val _imageFileUri = MutableStateFlow<Uri?>(null)
    val imageFileUri = _imageFileUri.asStateFlow()

    init {
        app.applicationContext.revokeSelfPermissionOnKill(Manifest.permission.CALL_PHONE)
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
}