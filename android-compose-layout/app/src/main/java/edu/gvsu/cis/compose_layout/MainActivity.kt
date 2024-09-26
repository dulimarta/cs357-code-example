package edu.gvsu.cis.compose_layout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.gvsu.cis.compose_layout.ui.theme.LayoutDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        RowDemo()
                        ColumnDemo()
                        BoxDemo()
                    }
                }
            }
        }
    }
}

@Composable
fun RowDemo(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .background(Color.Blue)
            .padding(16.dp)
            .background(Color.Green)
            .fillMaxWidth()
    ) {
        Image(
            painterResource(id = R.drawable.baseline_coffee_24),
            contentDescription = null, modifier = Modifier.size(40.dp),
            colorFilter = ColorFilter.tint(color = Color.Red)
        )
        Text("A cup of coffee")
    }
}

@Composable
fun ColumnDemo(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .background(Color.Cyan)
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
    ) {
        Image(
            painterResource(id = R.drawable.baseline_coffee_24),
            contentDescription = null,
            modifier = modifier.size(40.dp),
        )
        Text("A cup of coffee")
    }
}

@Composable
fun BoxDemo(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(Color.Yellow)
            .fillMaxWidth()
            .height(100.dp)
//            .fillMaxHeight(0.6f)
    ) {
        Text("A cup of coffee")
        Image(
            painterResource(id = R.drawable.baseline_coffee_24),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Green),
            modifier = modifier.size(40.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LayoutDemoTheme {
        RowDemo()
//        ColumnDemo()
//        BoxDemo()
    }
}