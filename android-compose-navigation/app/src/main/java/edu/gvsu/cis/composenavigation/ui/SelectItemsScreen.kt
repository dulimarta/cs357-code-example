package edu.gvsu.cis.composenavigation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import edu.gvsu.cis.composenavigation.OrderViewModel

@Composable
fun SelectItemsScreen(vm: OrderViewModel, onPlaceOrder: () -> Unit) {
    Column {
        Text("This is SelectItems")
        Button(
            onClick = {
                vm.addOrders(listOf("Celery", "Avocado"))
                onPlaceOrder()
            }) {
            Text("Place Order")
        }
    }
}