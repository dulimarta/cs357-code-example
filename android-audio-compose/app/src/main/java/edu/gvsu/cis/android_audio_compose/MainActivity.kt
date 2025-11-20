package edu.gvsu.cis.android_audio_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import edu.gvsu.cis.android_audio_compose.ui.theme.AndroidaudiocomposeTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appVM: AudioViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            AndroidaudiocomposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AudioScreen(
                        modifier = Modifier.padding(innerPadding),
                            vm = appVM)
                }
            }
        }
    }
}

fun sinePath(path: Path, size: Size, freq1: Float, freq2: Float) {
    path.moveTo(0f, size.height/2)
    // Use a sum of two sine waves of different frequency
    for (x in 0..size.width.toInt()) {
        val y1 = Math.sin(x * freq1 * Math.PI / size.width).toFloat()
        val y2 = Math.sin(x * freq2 * Math.PI / size.width).toFloat()
        val ySum = (0.4f * y1 + 0.6f * y2) * size.height / 2 + size.height / 2
        path.lineTo(x.toFloat(), ySum)
    }
}

@Composable
fun AudioScreen(modifier: Modifier = Modifier, vm: AudioViewModel) {
    val isPlayingAudio by vm.isPLayingAudio.collectAsState()
    var freq1 by remember { mutableStateOf(1f) }
    var freq2 by remember { mutableStateOf(2f) }
    val radioOptions = listOf("Piano", "Saxophone")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0])}
    LaunchedEffect(isPlayingAudio) {
        launch(Dispatchers.Default) {
            while (isPlayingAudio) {
                freq1 = 20 * Random.nextFloat()
                freq2 = 10 + 30 * Random.nextFloat()
                delay(100)
            }
            freq1 = 0f
            freq2 = 0f
        }
    }
    Column(modifier = modifier.padding(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Canvas(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .size(48.dp)) {
                val p = Path()
                sinePath(p, size, freq1, freq2)
                drawPath(p, SolidColor(Color.Black), style = Stroke(width = 4f))
            }
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { vm.startAudio() }, enabled = !isPlayingAudio) {
                Text(text = "Play")
            }
            Button(onClick = { vm.stopAudio() }, enabled = isPlayingAudio) {
                Text(text = "Stop")
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,) {
            radioOptions.forEach { text ->
                RadioButton(selected = (text == selectedOption),
                    onClick = { onOptionSelected(text)
                    vm.selectAudio(text)})
                Text(text = text)
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun AudioScreenPreview() {
//    AndroidaudiocomposeTheme {
//        AudioScreen("Android")
//    }
//}
