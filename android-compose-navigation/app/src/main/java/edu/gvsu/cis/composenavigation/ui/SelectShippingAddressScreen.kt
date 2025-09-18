package edu.gvsu.cis.composenavigation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun SelectShippingAddressScreen(onShipTo: (String) -> Unit) {

    Column {
        Text("Select Shipping Address", fontSize = 20.sp)
        Button(onClick = { onShipTo("49401")}) {
            Text("Allendale")
        }
    }

}