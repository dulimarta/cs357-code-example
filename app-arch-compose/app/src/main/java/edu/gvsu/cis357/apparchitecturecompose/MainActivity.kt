package edu.gvsu.cis357.apparchitecturecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import edu.gvsu.cis357.apparchitecturecompose.ui.theme.AppArchitectureComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppArchitectureComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var count1 by remember { mutableStateOf(0) }
    Column {
        Text(
            text = "Hello $count1",
            fontSize = 32.sp,
            modifier = modifier
        )
        Button(onClick = {
            count1 = count1.inc()
        }) {
            Text("Add 1")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppArchitectureComposeTheme {
        Greeting("Android")
    }
}