package edu.gvsu.cis.composenavigation

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.gvsu.cis.composenavigation.ui.theme.ComposeNavigationTheme

class GreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val n1 = intent?.getIntExtra("first", 0)!!
                    val n2 = intent?.getIntExtra("second", 0)!!
                    GreenUI(n1, n2)
                }
            }
        }
    }
}

@Composable
fun GreenUI(num1: Int, num2: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier.background(Color.hsl(150f, 0.5f, 0.6f)).padding(24.dp)) {

        val thisActivity = LocalContext.current as? Activity
        Text(
            text = "Received values: $num1 and $num2!",
            fontSize = 22.sp,

        )

        Button(onClick = { thisActivity?.finish() }) {
            Text("Go back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreenUIPreview() {
    ComposeNavigationTheme {
        GreenUI(20, 47)
    }
}