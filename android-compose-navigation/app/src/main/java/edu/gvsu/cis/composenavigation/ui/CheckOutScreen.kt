package edu.gvsu.cis.composenavigation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import edu.gvsu.cis.composenavigation.OrderViewModel

@Composable
fun CheckOutScreen(charge: Float, vm: OrderViewModel, onCheckOut: (Boolean) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Checking Out $charge")
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            Button(onClick = {
                onCheckOut(true)
            }) { Text("Confirm") }
            Button(onClick = { onCheckOut(false) }) {
                Text("Cancel")
            }
        }
    }

}