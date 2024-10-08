package edu.gvsu.cis357.android_firebase_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
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
        setContent {
            AndroidfirebasecomposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val z = innerPadding
                    AppScreen()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        otherViewModel.fetchMembers()
    }
}


@Serializable
sealed class Screen {

    @Serializable
    object Login

    @Serializable
    data class Main(val uid: String)
}

@Composable
fun AppScreen(modifier: Modifier = Modifier, navCtrl: NavHostController = rememberNavController()) {
    val authViewModel = viewModel<AuthViewModel>()
    NavHost(navController = navCtrl, startDestination = Screen.Login) {
        composable<Screen.Login> {

            LoginScreen(modifier, authViewModel) { x ->
                navCtrl.navigate(Screen.Main(x))
            }
        }
//        composable("main/{uid}") {
//            it.arguments?.getString("uid")?.let { uid ->
//                MainScreen(uid)
//            }
//        }
        composable<Screen.Main> { entry ->
            val zzz = entry.toRoute<Screen.Main>()
            MainScreen(modifier, zzz.uid)
        }
    }
}
