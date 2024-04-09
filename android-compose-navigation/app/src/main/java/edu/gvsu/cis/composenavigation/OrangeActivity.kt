package edu.gvsu.cis.composenavigation

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
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

class OrangeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val n1 = intent?.getIntExtra("first", 0) ?: 0
                    val n2 = intent?.getIntExtra("second", 0)  ?: 0
                    OrangeUI(n1, n2)
                }
            }
        }
    }
}

@Composable
fun OrangeUI(num1: Int, num2: Int, modifier: Modifier = Modifier) {
    val thisContext = LocalContext.current
    val thisActivity = thisContext as? Activity

    Column(modifier = modifier
        .background(Color.hsl(22f, 0.8f, 0.6f))
        .padding(24.dp)) {

        val thisActivity = LocalContext.current as? Activity
        Text(
            text = "Received values: $num1 and $num2!",
            fontSize = 22.sp,

        )

        Button(onClick = {
            val spBundle = Intent()
            spBundle.putExtra("sum", num1 + num2)
            spBundle.putExtra("product", num1 * num2)
            thisActivity?.setResult(RESULT_OK, spBundle)
            thisActivity?.finish()
        }) {
            Text("Compute Sum & Product")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrangeUIPreview() {
    ComposeNavigationTheme {
        OrangeUI(num1 = 20, 47)
    }
}