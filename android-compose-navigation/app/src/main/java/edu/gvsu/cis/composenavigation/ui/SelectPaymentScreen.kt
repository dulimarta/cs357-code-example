package edu.gvsu.cis.composenavigation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SelectPaymentScreen(totalCharge: Float, onPaymentSelected: (String) -> Unit) {
    Column {
        Text("Select your Payment for purchase amount $totalCharge")
        Button(onClick = {onPaymentSelected("Visa: 1234 5678 9999 0000")} ) {
            Text("Use Visa")
        }
    }
}