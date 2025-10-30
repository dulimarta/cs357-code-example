package edu.gvsu.cis357.retrofit_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import edu.gvsu.cis357.retrofit_compose.ui.theme.RetrofitcomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            val vm: MyViewModel by viewModels<MyViewModel>()
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
fun RandomNamesWithRetrofit(modifier: Modifier = Modifier, vm: MyViewModel) {
    Column(modifier = modifier.fillMaxHeight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(8.dp)) {
            Button(onClick = {
                vm.getUsers(5)
            }) {
                Text("Get more names")
            }
            if (!vm.users.isEmpty()) {
                Text("You have ${vm.users.size} names", modifier = Modifier.padding(horizontal = 4.dp))
            }
        }
        LazyColumn {
            itemsIndexed(vm.users) { pos, person ->
                Row(
                    modifier = Modifier
                        .background(if (pos % 2 == 0) Color.Yellow else Color.Cyan)
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    AsyncImage(modifier = Modifier.padding(end = 16.dp).size(54.dp),
                        model = person.picture.thumbnail, contentDescription = "thumbnail")
                    Column {
                        Text("${person.name.first} ${person.name.last}", fontSize = 16.sp)
                        Text("${person.email}", fontSize = 12.sp)
                    }
                }
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