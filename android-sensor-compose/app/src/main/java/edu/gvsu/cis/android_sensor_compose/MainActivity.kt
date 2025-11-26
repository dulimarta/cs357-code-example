package edu.gvsu.cis.android_sensor_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.gvsu.cis.android_sensor_compose.ui.theme.AndroidsensorcomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appVM: AppViewModel by viewModels()
            AndroidsensorcomposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SensorScreen(
                        modifier = Modifier.padding(innerPadding),
                        vm = appVM
                    )
                }
            }
        }
    }
}

fun Float.format(digits: Int) = "%.${digits}f".format(this)

fun Double.format(digits: Int) = "%.${digits}f".format(this)

fun Float.withUnit(): AnnotatedString {
    val formatted = this.format(2)
    return buildAnnotatedString {
        append(formatted)
        append(" m/s")
        withStyle(
            style = SpanStyle(
                fontSize = 16.sp,
                baselineShift = BaselineShift.Superscript
            )
        ) {
            append("2")
        }
    }
}

@Composable
fun SensorScreen(modifier: Modifier = Modifier, vm: AppViewModel) {
    val gForce by vm.gravity.collectAsState(GravitySensorData())
    var angle by remember { mutableStateOf(0.0) }
    Box(contentAlignment = Alignment.Center) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                buildAnnotatedString {
                    append("X: ")
                    append(gForce.x.withUnit())
                },
                fontSize = 20.sp
            )
            Text(
                buildAnnotatedString {
                    append("Y: ")
                    append(gForce.y.withUnit())
                },
                fontSize = 20.sp
            )
            Text(
                buildAnnotatedString {
                    append("Z: ")
                    append(gForce.z.withUnit())
                },
                fontSize = 20.sp
            )
            angle = if (Math.abs(gForce.z) >
                Math.max(Math.abs(gForce.x), Math.abs(gForce.y))
            ) 0.0
            else
                Math.atan2(gForce.x.toDouble(), gForce.y.toDouble())
            Text(
                "Angle ${Math.toDegrees(angle).format(3)}",
                fontSize = 24.sp
            )
        }
        Text(
            "Hello",
            modifier = Modifier.rotate(Math.toDegrees(angle).toFloat()),
            color = Color.Red,
            fontSize = 168.sp
        )
    }
}