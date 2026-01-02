package edu.gvsu.cis357.apparchitecturecompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TicketScreen(mod: Modifier = Modifier, vm: TicketViewModel) {
    var numTix by remember { mutableStateOf("") }
    val availableTicket by vm.availableTickets.collectAsState()

    Row(
        mod
            .padding(8.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column() {
            Text("Available tickets: $availableTicket", fontSize = 16.sp)
            OutlinedTextField(value = numTix, onValueChange = { numTix = it })
        }
        Button(
            onClick = { vm.purchaseTickets(numTix.toInt()) },
        ) {
            Text("Buy")
        }
    }
}

@Composable
fun TicketScreenByCategory(modifier: Modifier = Modifier, vm: TicketViewModel) {
    val ticketCategories = listOf("Regular", "Student")
    var numTix by remember { mutableStateOf("0") }
    val studentTix by vm.studentTix.collectAsState(0)
    val regularTix by vm.regularTix.collectAsState(0)
    val (selectedCategory, setSelectedCategory) = remember { mutableStateOf(ticketCategories[0]) }
    Column(modifier = modifier.padding(8.dp)) {
        Text("Student tickets available $studentTix")
        Text("Regular tickets available $regularTix")
//    Text("Student tickets available $studentTix")
        Row(verticalAlignment = Alignment.CenterVertically) {
            ticketCategories.forEach { cat ->
                RadioButton(
                    selected = cat == selectedCategory,
                    onClick = { setSelectedCategory(cat) }
                )
                Text(cat)
            }
        }
        OutlinedTextField(value = numTix, onValueChange = {
            numTix = it
        })
        Button(onClick = {
            vm.purchaseTicketByType(
                if (selectedCategory == "Regular") TicketType.Regular else TicketType.Student,
                numTix.toInt()
            )
        }) {
            Text("Buy")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TxPreview() {
    val vm = TicketViewModel()
    TicketScreenByCategory(vm = vm)
}