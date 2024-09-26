package edu.gvsu.cis.composedemo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.gvsu.cis.composedemo.ui.theme.ComposeDemoTheme

class MainActivity : ComponentActivity() {
    //    val vm: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        Text("Modify the counter(s) then change screen orientation")
                        CountWithLocalVariable()
                        CountWithLiveData()
                        CountByRememberMutableState()
                        CountBySavableMutableState()
                        CountWithSavableMutableState()
                        CountWithViewModel()
                    }
                }
            }
        }
    }
}

@Composable
fun CountWithLocalVariable() {
    // This code won't work
    var count: Int = 17
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = {
            count = count.inc()
            println("New count is $count")
        }, colors = ButtonDefaults.buttonColors(Color.Red, contentColor = Color.White)) {
            Text("+1")
        }
        Text(
            "Count is ${count} (Local Var: failed UI update)",
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun CountWithLiveData() {
    // This code won't work
    var count = MutableLiveData(41)
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = {
            count.value = count.value?.inc()
            println("New count is ${count.value}")
        }, colors = ButtonDefaults.buttonColors(Color.Red, contentColor = Color.White)) {
            Text("+1")
        }
        Text(
            "Count is ${count.value} (LiveData: failed UI update)",
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun CountByRememberMutableState() {
    var count by remember { mutableStateOf(0) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = {
            count = count + 1
            println("New Count is ${count}")
        }, colors = ButtonDefaults.buttonColors(Color(red = 255, green = 180, blue = 0))) {
            Text("+1")
        }
        Text(
            "Count is ${count}  (failed screen orientation)",
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun CountBySavableMutableState() {
    var count by rememberSaveable { mutableStateOf(0) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = {
            count = count + 1
            println("New Count is ${count}")
        }, colors = ButtonDefaults.buttonColors(Color.Green)) {
            Text("+1")
        }
        Text("Count is ${count}", modifier = Modifier.padding(start = 16.dp))
    }
}

@Composable
fun CountWithSavableMutableState() {
    var count = rememberSaveable { mutableStateOf(0) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = {
            count.value = count.value + 1
            println("New Count is ${count.value}")
        }, colors = ButtonDefaults.buttonColors(Color.Green)) {
            Text("+1")
        }
        Text("Count is ${count.value}", modifier = Modifier.padding(start = 16.dp))
    }
}
@Composable
fun CountWithViewModel(vm: MainActivityViewModel = viewModel()) {
    val countState by vm.counter.observeAsState()
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = {
            vm.countUp()
        }, colors = ButtonDefaults.buttonColors(Color.Green)) {
            Text("+1")
        }
        Text("Count is ${countState}", modifier = Modifier.padding(start = 16.dp))
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeDemoTheme {
        Column {
        }
    }
}