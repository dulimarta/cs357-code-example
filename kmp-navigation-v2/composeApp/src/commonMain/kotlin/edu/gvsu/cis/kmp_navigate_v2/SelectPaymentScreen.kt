package edu.gvsu.cis.kmp_navigate_v2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun SelectPaymentScreen(totalPay: Float, onUsePayment: (String?) -> Unit) {
    Column {
        Text("Total Payable: $totalPay. How would you like to pay?")
        val options = listOf("Credit Card", "PayPal", "Venmo", "Store credit")

        LazyVerticalGrid(horizontalArrangement = Arrangement.spacedBy(8.dp),
            columns = GridCells.Fixed(2)) {
            items(options) {
                Button(onClick = {
                    onUsePayment(it)
                }) {
                    Text("$it")
                }
            }
            item {
                Button(onClick = {
                    onUsePayment(null)
                }) {
                    Text("None")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectPaymentPreview() {
    SelectPaymentScreen(totalPay = 100f, onUsePayment = {})
}