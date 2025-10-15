package edu.gvsu.cis357.android_firebase_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import edu.gvsu.cis357.android_firebase_compose.ui.theme.AndroidfirebasecomposeTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        val authViewModel by viewModels<AuthViewModel>()
        val mainViewModel by viewModels<MyViewModel>()
        setContent {
            AndroidfirebasecomposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navCtrl: NavHostController = rememberNavController()
                    NavHost(modifier = Modifier.padding(innerPadding), navController = navCtrl, startDestination = Screen.Login) {
                        composable<Screen.Login> {

                            LoginScreen(authViewModel = authViewModel) { x ->
                                navCtrl.navigate(Screen.Main(x))
                            }
                        }
                        composable<Screen.Main> { entry ->
                            val screenData = entry.toRoute<Screen.Main>()
                            MainScreen(whoamI = screenData.uid, vm = mainViewModel)
                        }
                    }
                }
            }
        }
    }
}


@Serializable
sealed class Screen {

    @Serializable
    object Login

    @Serializable
    data class Main(val uid: String)
}