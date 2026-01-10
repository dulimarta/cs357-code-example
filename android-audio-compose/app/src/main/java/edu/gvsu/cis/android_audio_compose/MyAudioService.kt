package edu.gvsu.cis.android_audio_compose

import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

class MyAudioService: MediaSessionService() {
    private var mediaSession: MediaSession? = null


    @UnstableApi
    override fun onCreate() {
        super.onCreate()
        val playerBuffConfig = DefaultLoadControl.Builder()
            .setBufferDurationsMs(20000, 20000, 1000, 1000)
            .build()
        val player = ExoPlayer.Builder(this)
//            .setLoadControl(playerBuffConfig)
            .build()
        mediaSession = MediaSession.Builder(this, player).build()

    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession

}