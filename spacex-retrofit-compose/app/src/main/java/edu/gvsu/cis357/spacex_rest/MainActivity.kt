package edu.gvsu.cis357.spacex_rest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.gvsu.cis357.spacex_rest.model.LaunchItem
import edu.gvsu.cis357.spacex_rest.ui.theme.SpaceXRetrofitComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            val vm1 = MainViewModel()
            SpaceXRetrofitComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding), vm1
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier, vm: MainViewModel) {
    val name = vm.ceoName.collectAsState()
    val launches = vm.launches.collectAsState()
    val loading = rememberSaveable { mutableStateOf(false) }
    Box(modifier = modifier.fillMaxSize().padding(8.dp)) {
        Column {
            if (name.value != null) {
                loading.value = false
                Text(
                    text = "Hello ${name.value}",
                    modifier = modifier
                )
            } else if (loading.value) {
                LinearProgressIndicator(Modifier.fillMaxWidth())
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Button(onClick = {
                    loading.value = true
                    vm.sendCompanyRequest()
                }) {
                    Text("Company")
                }
                Button(onClick = {
                    loading.value = true
                    vm.sendLaunchesRequest()
                }) {
                    Text("Missions")
                }
            }
            if (launches.value.size > 0) {
                loading.value = false
                LazyColumn {
                    itemsIndexed(launches.value) {idx,item ->
                        LaunchItem(idx, item) {
                            println("Selection?")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LaunchItem(pos: Int, z: LaunchItem, itemClicked: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .border(1.dp, Color.Black)
            .padding(all = 4.dp)
            .clickable(onClick = { itemClicked(z.id!!) })
    ) {
        Text("${z.name!!} (${z.id!!})")
        Text(z.date_utc.toString())
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SpaceXRetrofitComposeTheme {
        Greeting(vm = MainViewModel())
    }
}