package edu.gvsu.cis.kmpaudiocontroller

import androidx.lifecycle.ViewModel

class AppViewModel: ViewModel() {
    private val ac by lazy { createAudioController() }

    fun startRecording() {
        ac.startRecording()
    }
    fun stopRecording() {
        ac.stopRecording()
    }

    fun playbackRecording() {
        ac.playbackRecording()
    }

}