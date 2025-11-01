package edu.gvsu.cis.intent_compose

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

// Reference: https://www.youtube.com/watch?v=KFazs62lIkE
// Topic: how to trigger snackbar from anywhere


object SnackbarController {
    private val _messageQueue = Channel<String>()
    val messageQueue = _messageQueue.receiveAsFlow()

    suspend fun sendMessage(msg: String) {
        _messageQueue.send(msg)
    }
}