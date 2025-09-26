package edu.gvsu.cis357.apparchitecturecompose

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TicketViewModel: ViewModel() {
    private val _availableTickets = MutableStateFlow(10)
    val availableTickets = _availableTickets.asStateFlow()

    fun purchseTickets(tixCount: Int) {
        if (tixCount <= _availableTickets.value)
            _availableTickets.value -= tixCount
    }
}