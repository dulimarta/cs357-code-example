package edu.gvsu.cis.composelazy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.gvsu.cis.composelazy.ui.theme.ComposeLazyTheme
import io.github.serpro69.kfaker.Faker


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vm = MyViewModel()
            ComposeLazyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumnDemo(viewModel = viewModel<MyViewModel>())
                }
            }
        }
    }
}

@Composable
fun LazyColumnDemo(modifier: Modifier = Modifier, viewModel: MyViewModel) {
    var selectedEntry by rememberSaveable { mutableStateOf<Int?>(null) }
    val names = viewModel.names
    Column {
        Text(
            text = "${names!!.size} random names",
        )
        if (selectedEntry != null)
            Text("Your selection: ${names!![selectedEntry!!]}")
        LazyColumn {
            itemsIndexed(names) { pos, name ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .selectable(false, onClick = {
                        if (selectedEntry == pos)
                            selectedEntry = null
                        else
                            selectedEntry = pos
                    }
                    ), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("${pos + 1} $name", fontSize = 26.sp)
                    if (selectedEntry?: -1 == pos) {
                        Image(imageVector = Icons.Default.Delete, "", modifier = Modifier.clickable {
                            viewModel.deleteItem(pos)
                            selectedEntry = null
                        })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeLazyTheme {
        LazyColumnDemo(viewModel = MyViewModel())
    }
}