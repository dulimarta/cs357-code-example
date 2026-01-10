package edu.gvsu.cis.kmp_navigate_v2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    vm: AppViewModel, paymentMethod: String?,
    onCheckoutComplete: (Boolean) -> Unit,
    onSelectPayment: (Float) -> Unit
) {
    val orderList by vm.orderList.collectAsState()
    val totalPrice by vm.totalPrice.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarState = remember { SnackbarHostState() }
    Column(modifier = modifier.fillMaxSize()) {
        Text("Checkout", fontSize = 24.sp)
        Card(
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text("Total purchase ${totalPrice.toCurrency()}")
                orderList.forEach {
                    Text("${it.description} x ${it.quantity}")
                }
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = { onSelectPayment(totalPrice) }) {
                        Text("Pay With...")
                    }
                    Text("Payment Method: ${paymentMethod ?: "<unknown>"}")
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { onCheckoutComplete(false) }) {
                Text("Cancel")
            }
            Button(
                enabled = paymentMethod != null,
                onClick = {
                    scope.launch {
                        val result = snackbarState.showSnackbar(
                            "Order Placed. Expect 2 days for delivery",
                            actionLabel = "OK",
                            duration = SnackbarDuration.Long
                        )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                            }

                            SnackbarResult.Dismissed -> {}
                        }
                        onCheckoutComplete(true)
                    }
                }) {
                Text("Confirm Order")
            }
        }
        SnackbarHost(snackbarState)
    }
}

@Preview(showBackground = true)
@Composable
fun CheckoutPreview() {
    CheckoutScreen(
        vm = viewModel(),
        paymentMethod = null, onCheckoutComplete = {}, onSelectPayment = {})
}