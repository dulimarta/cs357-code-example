package edu.gvsu.cis357.retrofit_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.gvsu.cis357.retrofit_compose.ui.theme.RetrofitcomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            val vm: MyViewModel by viewModels<MyViewModel>()
            var selectedRoute by rememberSaveable { mutableStateOf(0) }
            val navCtrl = rememberNavController()
            RetrofitcomposeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = selectedRoute == 0,
                                onClick = {
                                    selectedRoute = 0
                                    navCtrl.navigate(Route.RandomUser)
                                },
                                icon = { Icon(Icons.Default.Person, contentDescription = null) }
                            )
                            NavigationBarItem(
                                selected = selectedRoute == 1,
                                onClick = {
                                    selectedRoute = 1
                                    navCtrl.navigate(Route.Other)
                                },
                                icon = { Icon(Icons.Filled.Face, contentDescription = null) }
                            )
                        }
                    }) { innerPadding ->
                    NavHost(modifier = Modifier.padding(innerPadding), navController = navCtrl, startDestination = Route.RandomUser) {
                        composable<Route.RandomUser> {
                            RandomNamesWithRetrofit(vm =
                                vm
                            )
                        }
                        composable<Route.Other> {
                            Column(modifier = Modifier.padding(32.dp).fillMaxSize()) {
                                Text("This will be completed in class")
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RetrofitcomposeTheme {
//        val vm: MyViewModel = viewModel()
//
//        RandomNamesWithRetrofit(vm = vm)
    }
}