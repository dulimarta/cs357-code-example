package edu.gvsu.cis357.android_firebase_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.gvsu.cis357.android_firebase_compose.ui.theme.AndroidfirebasecomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            AndroidfirebasecomposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppScreen()
                }
            }
        }
    }
}

@Composable
fun AppScreen(navCtrl: NavHostController = rememberNavController()) {
    val authViewModel = AuthViewModel()
    NavHost(navController = navCtrl, startDestination = "login") {
        composable("login") {
            LoginScreen() {
                navCtrl.navigate("main/$it")
            }
        }
        composable("main/{uid}") {
            it.arguments?.getString("uid")?.let { uid ->
                MainScreen(uid)
            }
        }
    }
}
