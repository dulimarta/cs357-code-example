package edu.gvsu.cis.composelazy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.gvsu.cis.composelazy.ui.theme.ComposeLazyTheme
import io.github.serpro69.kfaker.Faker

val faker = Faker()

fun randomNames() = (1..100).map {
    "${faker.name.firstName()} ${faker.name.lastName()}"
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLazyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumnDemo(randomNames())
                }
            }
        }
    }
}

@Composable
fun LazyColumnDemo(names: List<String>, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "${names.size} random names",
        )
        LazyColumn {

            itemsIndexed(names) {pos, name ->
                Row(modifier = Modifier.selectable(false, onClick = {
                    println("Row $pos selected")
                })) {
                    Text("${pos + 1} $name")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeLazyTheme {
        LazyColumnDemo(randomNames())
    }
}