package edu.gvsu.cis.composenavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationDemoWithRoute(navController: NavHostController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Green)
            .padding(16.dp)
    ) {
        Text("The bottom-half is Composable-Route-Based")

        NavHost(navController, startDestination = "main") {
            composable("main") {
                val newPop = it.savedStateHandle.get<Int>("rounded")
                it.savedStateHandle.remove<Int>("rounded")
                newPop?.let {
                    println("Rounded population is $it")
                }
                MainScreen(navController)
            }
            composable("details/{city}/{population}") {
                it.arguments?.let {
                    val c = it.getString("city")
                    val pop = it.getString("population")
                    CityDetails(c ?: "None", pop?.toInt() ?: 1000, navController)
                }
            }
            composable("userPref") {
                UserSettingScreen(navController)
            }
        }
        Text("Below navigation host?")
    }

}

@Composable
fun MainScreen(navController: NavController) {
    var first by rememberSaveable {
        mutableStateOf("")
    }
    var second by rememberSaveable {
        mutableStateOf("")
    }
    Column {
//        TextField(value = first, onValueChange = {
//            first = it
//        }, label = { Text("First Number") })
//        TextField(value = second, onValueChange = {
//            second = it
//        }, label = { Text("Second Number") })
        Row {
            Button(onClick = {
                navController.navigate(route = "userPref")
            })
            {
                Text("1-way")
            }

            Button(onClick = {
                navController.navigate("details/Grand Rapids/387612")
            }) {
                Text("2-way")

            }
        }
    }
}

@Composable
fun UserSettingScreen(navController: NavHostController) {
    Column {
        Text("User Prefs here")
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}


@Composable
fun CityDetails(name: String, population: Int, navController: NavHostController) {
    Column {
        Text("City $name with population $population")
        Button(onClick = {
            val delta = 1000 - population % 1000
            navController.previousBackStackEntry?.savedStateHandle?.set("rounded", population + delta)
            navController.popBackStack()
        }) {
            Text("Round Up Population")
        }
    }
}