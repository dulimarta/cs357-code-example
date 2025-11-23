package edu.gvsu.cis.android_alarms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import edu.gvsu.cis.android_alarms.ui.theme.AndroidalarmsTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            AndroidalarmsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    JustText(modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}

@Composable
fun JustText(modifier: Modifier = Modifier) {
    val thisActivity = LocalActivity.current
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Second Activity",
            fontSize = 30.sp,
        )
        Button(onClick = { thisActivity?.finish() }) {
            Text("Close")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun JustTextPreview() {
    AndroidalarmsTheme {
        JustText()
    }
}