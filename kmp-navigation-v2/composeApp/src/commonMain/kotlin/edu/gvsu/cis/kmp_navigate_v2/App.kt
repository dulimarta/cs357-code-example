package edu.gvsu.cis.kmp_navigate_v2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.paleblueapps.kmpcore.formatters.number.NumberFormatter
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview


@Serializable
data object SelectItems

@Serializable
data object ShoppingCart

@Serializable
data object Checkout

@Serializable
data class SelectPayment(val totalPay: Float)


@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        // Must initialize the ViewModel using the constructor, not the factory style!!!
        val vm: AppViewModel = viewModel {
            AppViewModel()
        }


        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NavHost(navController, startDestination = SelectItems) {
                composable<SelectItems> {
                    SelectItemsScreen(
                        vm = vm,
                        onSelectionComplete = {
                            navController.navigate(ShoppingCart)
                        }
                    )
                }
                composable<ShoppingCart> {
                    ShoppingCartScreen(
                        vm = vm,
                        onShopCancel = {
                            navController.popBackStack()
                        },
                        onCheckout = {
                            navController.navigate(Checkout)
                        }
                    )
                }
                composable<Checkout> {
                    val payWith = it.savedStateHandle.get<String>("PAY_METHOD")
                    CheckoutScreen(
                        vm = vm,
                        paymentMethod = payWith,
                        onCheckoutComplete = { isComplete ->
                            vm.resetOrder()
                            navController.popBackStack(SelectItems, inclusive = false)
                        },
                        onSelectPayment = { totalPay ->
                            navController.navigate(SelectPayment(totalPay))
                        }
                    )
                }
                composable<SelectPayment> { stackEntry ->
                    val selectPaymentArg = stackEntry.toRoute<SelectPayment>()
                    SelectPaymentScreen(totalPay = selectPaymentArg.totalPay,
                        onUsePayment = {payMethod ->
                            navController.previousBackStackEntry?.savedStateHandle?.set("PAY_METHOD", payMethod)
                            navController.popBackStack()
                        })
                }
            }
        }
    }
}

val numberFormatter = NumberFormatter()

fun Float.toCurrency(): String = "$" + numberFormatter.format(this.toDouble())