package edu.gvsu.cis.kmp_navigate_v2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShoppingCartScreen(modifier: Modifier = Modifier,
                       vm: AppViewModel, onShopCancel: () -> Unit, onCheckout: () -> Unit) {
    val orderList by vm.orderList.collectAsState()
    Column(modifier = modifier.fillMaxWidth()) {
        Text("Shopping Cart (${orderList.size} items)", fontSize = 20.sp)
        Text("Total: ${vm.totalPrice.collectAsState().value.toCurrency()}",
            fontSize = 16.sp)
        LazyColumn(modifier = Modifier.padding(start = 12.dp)) {
            items(orderList) {
                Text("${it.description} (${it.quantity})")
            }
        }
        Row(modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onShopCancel) {
                Text("Cancel")
            }
            Button(onClick = onCheckout) {
                Text("Checkout")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingCartPreview() {
    ShoppingCartScreen(vm = viewModel(), onShopCancel = {}, onCheckout = {})
}