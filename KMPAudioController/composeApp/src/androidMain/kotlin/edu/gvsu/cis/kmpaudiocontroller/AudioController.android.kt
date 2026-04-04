package edu.gvsu.cis.kmpaudiocontroller

import android.content.Context
import android.media.MediaRecorder
import android.net.Uri
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C.AUDIO_CONTENT_TYPE_MUSIC
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import org.koin.java.KoinJavaComponent.getKoin
import java.io.File
import java.io.IOException

class AndroidAudioController(val context: Context) : AudioController {
    private var recorder: MediaRecorder? = null
    private var player: ExoPlayer? = null
    private var audioFile: String = ""

    private fun createRecorder() = MediaRecorder(context).apply {
        setAudioSource(MediaRecorder.AudioSource.MIC)
        setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        val auPath = context.filesDir?.absolutePath
        audioFile = "$auPath/audio_rec.m4a"
        setOutputFile(audioFile)
        setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
    }

    override fun startRecording(/*fileName: String*/) {
        try {
            if (recorder == null) recorder = createRecorder()
            recorder?.prepare()
            recorder?.start()
        } catch (e: IOException) {
            println("Can't initialize MediaRecorder ${e.message}")
        }
    }

    override fun stopRecording(onCompleted: (String?) -> Unit) {
        if (recorder != null) {
            recorder?.stop()
            recorder?.release()
            onCompleted(audioFile)
        } else {
            onCompleted(null)
        }
    }

    override fun playbackRecording() {
        if (audioFile.isEmpty()) return
        if (player == null) player = ExoPlayer.Builder(context).build()
        player?.apply {
            val uri = Uri.fromFile(File(audioFile))
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            play()
        }
    }

}

actual fun createAudioController(): AudioController {
    val context = getKoin().get<Context>()
    return AndroidAudioController(context)
}
