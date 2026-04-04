package edu.gvsu.cis.kmpaudiocontroller

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.AVFAudio.AVAudioRecorder
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryOptionDefaultToSpeaker
import platform.AVFAudio.AVAudioSessionCategoryPlayAndRecord
import platform.AVFAudio.AVFormatIDKey
import platform.AVFAudio.AVNumberOfChannelsKey
import platform.AVFAudio.setActive
import platform.CoreAudioTypes.kAudioFormatMPEG4AAC
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

class IosAudioController : AudioController {
    private var recorder: AVAudioRecorder? = null
    private var player: AVAudioPlayer? = null
    private var audioSession: AVAudioSession? = null
    private var audioFile: String = ""

    @OptIn(ExperimentalForeignApi::class)
    private fun createRecorder(): AVAudioRecorder {
        val recSettings = mapOf<Any?, Any>(
            AVFormatIDKey to kAudioFormatMPEG4AAC.toInt(),
            AVNumberOfChannelsKey to 1,
        )
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = true,
            error = null
        )
        audioFile = documentDirectory?.path + "/audio.m4a"
        player = null
        println("File recorded in $audioFile")
        return AVAudioRecorder(
            uRL = NSURL(string = audioFile),
            settings = recSettings,
            error = null
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun createAudioSession() = AVAudioSession.sharedInstance().apply {
        try {
            setCategory(
                AVAudioSessionCategoryPlayAndRecord,
                AVAudioSessionCategoryOptionDefaultToSpeaker,
                error = null
            )
            setActive(true, null)
        } catch (e: Exception) {
            println("Unable to create audion session ${e.message}")
        }
    }


    override fun startRecording() {
        if (audioSession == null) audioSession = createAudioSession()
        if (recorder == null) recorder = createRecorder()
        recorder?.let {
            it.prepareToRecord()
            it.record()
        }
    }

    override fun stopRecording(onCompleted: (String?) -> Unit) {
        if (recorder != null) {
            recorder?.stop()
            onCompleted(audioFile)
        } else
            onCompleted(null)
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun playbackRecording() {
        if (audioFile.isEmpty()) return
        if (player == null) {
            player = AVAudioPlayer(contentsOfURL = NSURL(string = audioFile), error = null)
        }
        player?.currentTime = 0.0
        player?.prepareToPlay()
        player?.play()
    }
}

actual fun createAudioController(): AudioController = IosAudioController()