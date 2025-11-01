package edu.gvsu.cis.intent_compose

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File

class AppViewModel(app: Application): AndroidViewModel(app) {
    private val context: Context = app.applicationContext
    private val _imageFileUri = MutableStateFlow<Uri?>(null)
    val imageFileUri = _imageFileUri.asStateFlow()

    fun setupImageUri(filename: String) {
        val imagePath = File(context.filesDir, "images")
//        val imagePath = File(context.cacheDir, "demo_images")
        if (!imagePath.exists())
            imagePath.mkdirs()
        val tmpFile = File.createTempFile(filename, ".jpg", imagePath)
        _imageFileUri.update {
            FileProvider.getUriForFile(
                context, context.packageName + ".provider",
                tmpFile
            )
        }
    }

    fun removeImageFile() {
        _imageFileUri.value?.let {
            context.contentResolver.delete(it, null, null)
        }
    }
}