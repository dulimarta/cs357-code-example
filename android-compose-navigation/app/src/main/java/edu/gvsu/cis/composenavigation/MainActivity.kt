package edu.gvsu.cis.composenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import edu.gvsu.cis.composenavigation.ui.CheckOutScreen
import edu.gvsu.cis.composenavigation.ui.SelectItemsScreen
import edu.gvsu.cis.composenavigation.ui.SelectPaymentScreen
import edu.gvsu.cis.composenavigation.ui.SelectShippingAddressScreen
import edu.gvsu.cis.composenavigation.ui.ShoppingCartScreen
import edu.gvsu.cis.composenavigation.ui.theme.ComposeNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val orderViewModel by viewModels<OrderViewModel>()
        setContent {
            ComposeNavigationTheme {
                // A surface container using the 'background' color from the theme
                val nc = rememberNavController()
                Scaffold (
                    modifier = Modifier.fillMaxSize(),
                ) { padding ->
                    NavHost(modifier = Modifier.padding(padding),
                        navController = nc, startDestination = Screen.SelectItems) {
                        composable<Screen.SelectItems> {
                            SelectItemsScreen(orderViewModel) {
                                nc.navigate(Screen.ShoppingCart)
                            }
                        }
                        composable<Screen.ShoppingCart> {
                            val payMethod = it.savedStateHandle.get<String>("PAY_WITH")
                            val destination = it.savedStateHandle.get<String>("SHIP_TO")
                            ShoppingCartScreen(orderViewModel, payWith = payMethod,
                                shipTo = destination,
                                onSelectAddress = {
                                nc.navigate(Screen.SelectShippingAddress)
                            }, onSelectPayment = {
                                val destination = Screen.SelectPayment(it)
                                nc.navigate(destination)
                            }, onCheckOut = {
                                val dest = Screen.Checkout(it)
                                    nc.navigate(dest)
                                })
                        }
                        composable<Screen.SelectShippingAddress> {
                            SelectShippingAddressScreen {
                                nc.previousBackStackEntry?.savedStateHandle?.set("SHIP_TO", it)
                                nc.popBackStack()
                            }
                        }
                        composable<Screen.SelectPayment> {
                            val args = it.toRoute<Screen.SelectPayment>()
                            SelectPaymentScreen(args.totalCharge) {
                                nc.previousBackStackEntry?.savedStateHandle?.set("PAY_WITH", it)
                                nc.popBackStack()
                            }
                        }
                        composable<Screen.Checkout> {
                            val args = it.toRoute<Screen.Checkout>()
                            CheckOutScreen(args.totalCharge, orderViewModel) {confirmed ->
                                nc.popBackStack(Screen.SelectItems, false)
                            }
                        }
                    }
                }
            }
        }
    }
}
