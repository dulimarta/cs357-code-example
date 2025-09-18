package edu.gvsu.cis.composenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import edu.gvsu.cis.composenavigation.ui.CheckOutScreen
import edu.gvsu.cis.composenavigation.ui.SelectItemsScreen
import edu.gvsu.cis.composenavigation.ui.SelectPaymentScreen
import edu.gvsu.cis.composenavigation.ui.SelectShippingAddressScreen
import edu.gvsu.cis.composenavigation.ui.ShoppingCartScreen
import edu.gvsu.cis.composenavigation.ui.theme.ComposeNavigationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val orderViewModel by viewModels<OrderViewModel>()
        setContent {
            ComposeNavigationTheme {
                // A surface container using the 'background' color from the theme
                val nc = rememberNavController()
                val navBackStackEntry by nc.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val atHome = currentRoute?.endsWith("SelectItems") ?: false
                Scaffold (
                    topBar = {
                        TopAppBar(
                            title = {Text("Simple Shopping")},
                            navigationIcon = {
                                if (!atHome)
                                IconButton(onClick = { nc.popBackStack() }) {
                                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                                }
                            },
                            colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.LightGray)
                        )
                    },
                    modifier = Modifier.fillMaxSize(),
                ) { padding ->
                    NavHost(modifier = Modifier.padding(padding),
                        navController = nc, startDestination = Route.SelectItems) {
                        composable<Route.SelectItems> {
                            SelectItemsScreen(orderViewModel) {
                                nc.navigate(Route.ShoppingCart)
                            }
                        }
                        composable<Route.ShoppingCart> {
                            val payMethod = it.savedStateHandle.get<String>("PAY_WITH")
                            val destination = it.savedStateHandle.get<String>("SHIP_TO")
                            ShoppingCartScreen(orderViewModel, payWith = payMethod,
                                shipTo = destination,
                                onSelectAddress = {
                                nc.navigate(Route.SelectShippingAddress)
                            }, onSelectPayment = {
                                val destination = Route.SelectPayment(it)
                                nc.navigate(destination)
                            }, onCheckOut = {
                                val dest = Route.Checkout(it)
                                    nc.navigate(dest)
                                })
                        }
                        composable<Route.SelectShippingAddress> {
                            SelectShippingAddressScreen {
                                nc.previousBackStackEntry?.savedStateHandle?.set("SHIP_TO", it)
                                nc.popBackStack()
                            }
                        }
                        composable<Route.SelectPayment> {
                            val args = it.toRoute<Route.SelectPayment>()
                            SelectPaymentScreen(args.totalCharge) {
                                nc.previousBackStackEntry?.savedStateHandle?.set("PAY_WITH", it)
                                nc.popBackStack()
                            }
                        }
                        composable<Route.Checkout> {
                            val args = it.toRoute<Route.Checkout>()
                            CheckOutScreen(args.totalCharge, orderViewModel) {confirmed ->
                                nc.popBackStack(Route.SelectItems, false)
                            }
                        }
                    }
                }
            }
        }
    }
}
