package edu.gvsu.cis.composenavigation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import edu.gvsu.cis.composenavigation.OrderViewModel

@Composable
fun ShoppingCartScreen(
    vm: OrderViewModel,
    shipTo: String? = null, payWith: String? = null,
    onSelectPayment: (Float) -> Unit, onSelectAddress: () -> Unit,
    onCheckOut: (Float) -> Unit
) {
    val items by vm.orders.collectAsState()
    Column(modifier = Modifier
        .background(Color(0x45120012))
        .fillMaxSize()) {
        Text("This is your Shopping Cart")
        LazyColumn {
            itemsIndexed(items = items) { idx, item ->
                Text("$idx $item")
            }
        }

        if (shipTo != null) {
            Text("Your orders will be shipped to $shipTo")
        }
        if (payWith != null) {
            Text("Your orders will be paid with $payWith")
        }

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround) {
            Button({ onSelectAddress() }) {
                Text("Ship Address")
            }
            Button({ onSelectPayment(500f) }) {
                Text("Payment")
            }
            Button({onCheckOut(125f)}) {
                Text("Checkout")
            }
        }
    }
}