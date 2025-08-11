package edu.gvsu.cis357.spacex_graphql

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.gvsu.cis357.spacex_graphql.ui.theme.SpaceXGraphQLComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm1 = MainViewModel()
            SpaceXGraphQLComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding),
                        vm1
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier, vm : MainViewModel) {
    val name =  vm.ceoName.collectAsState()
    val loading = rememberSaveable { mutableStateOf(false) }
    Column(modifier = modifier.padding(all = 8.dp).fillMaxSize()) {
        if(name.value != null) {
            loading.value = false
            Text(
                text = "Hello ${name.value}"
            )
        }
        else if (loading.value) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        Button(onClick = {
            loading.value = true
            vm.sendQuery()
        }) {
            Text("Get")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    SpaceXGraphQLComposeTheme {
        Greeting(vm = MainViewModel())
    }
}