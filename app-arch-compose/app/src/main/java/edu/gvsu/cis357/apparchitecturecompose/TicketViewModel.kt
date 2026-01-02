package edu.gvsu.cis357.apparchitecturecompose

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

enum class TicketType { Regular, Student}

data class Ticket(val category: TicketType = TicketType.Regular, val available: Int)

class TicketViewModel: ViewModel() {
    private val _availableTickets = MutableStateFlow(10)
    val availableTickets = _availableTickets.asStateFlow()

    private val _regularTickets = MutableStateFlow(Ticket(TicketType.Regular, 20))
    private val _studentTickets = MutableStateFlow(Ticket(TicketType.Student, 10))

    val regularTix = _regularTickets.asStateFlow().map { it.available }
    val studentTix = _studentTickets.asStateFlow().map { it.available }

    fun purchaseTickets(tixCount: Int) {
        if (tixCount <= _availableTickets.value)
            _availableTickets.value -= tixCount
    }

    fun purchaseTicketByType (t: TicketType, tixCount: Int) {
        when (t) {
            TicketType.Regular -> _regularTickets.update {
                it.copy(available = it.available - tixCount)
            }
            TicketType.Student -> _studentTickets.update {
                it.copy(available = it.available - tixCount)
            }
        }
    }
}