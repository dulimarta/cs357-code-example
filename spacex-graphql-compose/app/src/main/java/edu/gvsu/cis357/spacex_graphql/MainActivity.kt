package edu.gvsu.cis357.spacex_graphql

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
    val launches = vm.launches.collectAsState()
    val loading = rememberSaveable { mutableStateOf(false) }
    val selectedLaunch = rememberSaveable { mutableStateOf<AllLaunchesQuery.Launch?>(null) }
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .padding(all = 8.dp)
//                .fillMaxSize()
        ) {
            if (name.value != null) {
                loading.value = false
                Text(
                    text = "Hello ${name.value}"
                )
            } else if (loading.value) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = {
                    loading.value = true
                    vm.sendCompanyQuery()
                }) {
                    Text("Company")
                }
                Button(onClick = {
                    loading.value = true
                    vm.sendMissionQuery()
                }) {
                    Text("Missions")
                }
            }
            if (launches.value.size > 0) {
                loading.value = false
                LazyColumn {
                    itemsIndexed(launches.value) { idx, item ->
                        LaunchItem(idx, item) {
                            println("Selected $it")
                            selectedLaunch.value = launches.value.get(idx)
                        }
                    }
                }
            }
        }
        if (selectedLaunch.value != null) {
            LaunchDialog(selectedLaunch.value!!) {
                selectedLaunch.value = null
            }
        }
    }
}

@Composable
private fun LaunchItem(pos: Int, z: AllLaunchesQuery.Launch, itemClicked: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .border(1.dp, Color.Black)
            .padding(all = 4.dp)
            .clickable(onClick = { itemClicked(z.id!!) })
    ) {
        Text("${z.mission_name!!} (${z.id!!})")
        Text(z.launch_date_utc.toString())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LaunchDialog(z: AllLaunchesQuery.Launch, onDismiss: () -> Unit) {
    BasicAlertDialog (
        onDismissRequest = onDismiss,
    ) {
        Column(modifier = Modifier.background(Color.White).padding(8.dp)) {
            Text("Mission ${z.mission_name}", fontSize = 20.sp)
            Text("Launch site ${z.launch_site?.site_name_long ?: "Unknown"}")
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