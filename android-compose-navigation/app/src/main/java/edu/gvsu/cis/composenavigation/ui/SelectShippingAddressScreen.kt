package edu.gvsu.cis.composenavigation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SelectShippingAddressScreen(onShipTo: (String) -> Unit) {

    Column {
        Text("Select Shipping Address")
        Button(onClick = { onShipTo("49401")}) {
            Text("Allendale")
        }
    }

}