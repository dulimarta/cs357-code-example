package edu.gvsu.cis.kmpaudiocontroller

interface AudioController {
    fun startRecording(/*filePath: String, fileName: String */)
    fun stopRecording(onCompleted: (String?) -> Unit = {})

    fun playbackRecording()
}

expect fun createAudioController(): AudioController
