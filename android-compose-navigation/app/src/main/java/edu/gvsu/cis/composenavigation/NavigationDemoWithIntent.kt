package edu.gvsu.cis.composenavigation

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.actions.ReserveIntents
import edu.gvsu.cis.composenavigation.ui.theme.ComposeNavigationTheme


@Composable
fun NavigationDemoWithIntent(modifier: Modifier = Modifier) {
    var firstValue by rememberSaveable {
        mutableStateOf("")
    }
    var secondValue by rememberSaveable {
        mutableStateOf("")
    }
    var sum by rememberSaveable {
        mutableStateOf(0)
    }
    var product by rememberSaveable {
        mutableStateOf(0)
    }
    val orangeLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                it.data?.let {
                    val s = it.getIntExtra("sum", 0)
                    val p = it.getIntExtra("product", 0)
                    sum = s
                    product = p
                }
            }
        }
    val thisContext = LocalContext.current
    val thisActivity = thisContext as? Activity
    Column(
        modifier = modifier
            .background(Color.Yellow)
            .padding(16.dp)
            .wrapContentHeight()
    ) {
        Text("The top-half is Intent-Based")
        TextField(value = firstValue, onValueChange = {
            firstValue = it
        }, label = {
            Text("First Value")
        })
        TextField(value = secondValue, onValueChange = {
            secondValue = it
        }, label = {
            Text("Second Value")
        })

        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Button(onClick = {
//                val myIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:616-331-0000"))
//                val myIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) // Start camera

//                val myIntent = Intent(Intent.ACTION_VIEW,
//                    Uri.parse("https://www.gvsu.edu")) // Web Browser
                val myIntent = Intent(Intent.ACTION_VIEW,
                        Uri.parse("geo:42.9648, -85.89?z=16")) // GVSU Map
                thisActivity?.startActivity(myIntent)
//                if (myIntent.resolveActivity(thisContext.packageManager) != null) {
//                    thisActivity?.startActivity(myIntent)
//                }
            }) {
                Text("External Intent")
            }
            Button(onClick = {
                val toGreen = Intent(thisContext, GreenActivity::class.java)
                toGreen.putExtra("first", firstValue.trim().toIntOrNull() ?: 0)
                toGreen.putExtra("second", secondValue.trim().toIntOrNull() ?: 0)
                thisActivity?.startActivity(toGreen)
            }, colors = ButtonDefaults.buttonColors(Color.hsl(150f, 0.5f, 0.6f))) {
                Text("1-way")
            }

            Button(
                onClick = {
                    val toOrange = Intent(thisContext, OrangeActivity::class.java)
                    toOrange.putExtra("first", firstValue.toIntOrNull() ?: 0)
                    toOrange.putExtra("second", secondValue.toIntOrNull() ?: 0)
                    orangeLauncher.launch(toOrange)
                },
                colors = ButtonDefaults.buttonColors(Color.hsl(22f, 0.8f, 0.6f))
            ) {
                Text("2-way")
            }
        }

        Text("Sum is $sum, product is $product")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeNavigationTheme {
        NavigationDemoWithIntent()
    }
}