package edu.gvsu.cis.composenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import edu.gvsu.cis.composenavigation.ui.theme.ComposeNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationDemo()
//                    NavigationDemoWithRoute(rememberNavController())
                }
            }
        }
    }
}

@Composable
fun NavigationDemo() {
    Column() {
        NavigationDemoWithIntent(Modifier.weight(1f, true))
        NavigationDemoWithRoute(rememberNavController(), modifier = Modifier.weight(1f, false))
    }
}

@Preview
@Composable fun MyPreview() {
    ComposeNavigationTheme {
        NavigationDemo()
    }

}