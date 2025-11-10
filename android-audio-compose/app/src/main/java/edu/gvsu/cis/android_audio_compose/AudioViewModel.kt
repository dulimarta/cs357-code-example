package edu.gvsu.cis.android_audio_compose

import android.app.Application
import android.content.ComponentName
import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.REPEAT_MODE_ONE
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AudioViewModel(val app: Application) : AndroidViewModel(app) {

    private var _mediaController = MutableStateFlow<MediaController?>(null)
    val mediaController = _mediaController.asStateFlow()
    private var _playingAudio = MutableStateFlow(false)
    val isPLayingAudio = _playingAudio.asStateFlow()
    private var currentMP3resource: Int = R.raw.piano_short
    init {
        val sessionToken = SessionToken(
            app.applicationContext,
            ComponentName(app.applicationContext, MyAudioService::class.java)
        )
        val ctrlFuture = MediaController.Builder(app.applicationContext, sessionToken)
            .buildAsync()
        ctrlFuture.addListener({
            _mediaController.update { ctrlFuture.get() }
            _playingAudio.value = _mediaController.value?.isPlaying == true
        }, MoreExecutors.directExecutor())
    }

    fun startAudio() {
        _mediaController.value?.run {
            val uri = prepareMP3(currentMP3resource)
            val mediaItem = MediaItem.fromUri(uri)
            repeatMode = REPEAT_MODE_ONE
            setMediaItem(mediaItem)
            prepare()
            play()

        }
        _playingAudio.value = true
    }

    private fun prepareMP3(resId: Int): Uri =
        Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE) // android.resource://
            .authority(app.packageName)
            .appendPath(resId.toString())
            .build()

    fun selectAudio(choice: String) {
        when (choice) {
            "Piano" -> currentMP3resource = R.raw.piano_short
            "Saxophone" -> currentMP3resource = R.raw.saxophone
        }
        if (_playingAudio.value) {
            stopAudio()
            startAudio()
        }
    }

    fun stopAudio() {
        _playingAudio.value = false
        _mediaController.value?.stop()
    }
}