package edu.gvsu.cis357.apparchitecturecompose

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun TicketScreen(mod: Modifier = Modifier, vm: TicketViewModel) {
    var numTix by remember { mutableStateOf("") }
    val availableTicket by vm.availableTickets.collectAsState()
    Column(mod) {
        Text("Available ticket $availableTicket")
        OutlinedTextField(value = numTix, onValueChange = { numTix = it})
        Button(onClick = {vm.purchseTickets(numTix.toInt())}) {
            Text("Buy")
        }
    }
}