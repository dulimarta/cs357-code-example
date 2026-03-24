package edu.gvsu.cis.ktorclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import edu.gvsu.cis.ktorclient.ui.theme.KTorClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm by viewModels<MyViewModel>()
            KTorClientTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WebServiceDemo(modifier = Modifier.padding(innerPadding), viewModel = vm)
                }
            }
        }
    }
}

@Composable
fun WebServiceDemo(modifier: Modifier = Modifier, viewModel: MyViewModel ) {
    val httpResponse by viewModel.rawResponse.collectAsState()
    var exprInput by remember {mutableStateOf("")}
    Column() {
        Button(modifier = modifier, onClick = {
//            viewModel.getSomething()
            viewModel.getNewtonExpression(exprInput)
        }) {
            Text(text = "Get Something")
        }
        TextField(exprInput, onValueChange = {
            exprInput = it
        })
        Text(text = httpResponse, fontSize = 32.sp)
    }
}