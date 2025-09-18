package edu.gvsu.cis.composenavigation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import edu.gvsu.cis.composenavigation.OrderViewModel

@Composable
fun SelectItemsScreen(vm: OrderViewModel, onPlaceOrder: () -> Unit) {
    Column {
        Text("You are about to buy Avocado & Celery")
        Button(
            onClick = {
                vm.addOrders(listOf("Celery", "Avocado"))
                onPlaceOrder()
            }) {
            Text("Place Order")
        }
    }
}