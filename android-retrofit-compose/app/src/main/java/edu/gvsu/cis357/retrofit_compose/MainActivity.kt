package edu.gvsu.cis357.retrofit_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.gvsu.cis357.retrofit_compose.ui.theme.RetrofitcomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            val vm: MyViewModel = viewModels<MyViewModel>().value
            RetrofitcomposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RandomNamesWithRetrofit(
                        modifier = Modifier.padding(innerPadding),
                        vm
                    )
                }
            }
        }
    }
}

@Composable
fun RandomNamesWithRetrofit(modifier: Modifier = Modifier, vm:MyViewModel) {
    Column {
        Text("We are here")
        Button(onClick = {
            vm.getUsers()
        }) {
            Text("Get more names")
        }

        LazyColumn {
            items(vm.users) {
                Text("${it.name.first} ${it.name.last}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RetrofitcomposeTheme {
//        val vm: MyViewModel = viewModel()
//
//        RandomNamesWithRetrofit(vm = vm)
    }
}