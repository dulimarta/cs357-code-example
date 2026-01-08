package edu.gvsu.cis.kmpwordscamblersharedvm

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kmpwordscamblersharedvm.composeapp.generated.resources.Res
import kmpwordscamblersharedvm.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    val vm = viewModel<GameViewModel>()
    MaterialTheme {
        GameScreen(viewModel = vm, modifier = Modifier
            .windowInsetsPadding(WindowInsets.safeContent)
        )
    }
}

@Composable
fun GameScreen(modifier: Modifier = Modifier, viewModel: GameViewModel) {
    val secret by viewModel.secretWord.collectAsStateWithLifecycle()
    val letters by viewModel.letters.collectAsStateWithLifecycle()
    val removed by viewModel.removedLetter.collectAsStateWithLifecycle()
    val solved by viewModel.solved.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.generateNewWord()
    }
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = { viewModel.generateNewWord() }) {
            Text("New Secret Game")
        }
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxHeight()) {
            if (removed != null)
                BigLetter(removed, modifier = Modifier.offset(y = -60.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                letters.forEachIndexed { pos, letter ->

                    BigLetter(
                        letter, modifier = Modifier.clickable(
                            onClick = { viewModel.clickAtPos(pos) }
                        ))
                }
            }
            if (solved)
                Text(
                    "You got it", fontSize = 24.sp,
                    modifier = Modifier.offset(y = 60.dp)
                )
        }
    }
}

@Composable
fun BigLetter(letter: String?, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(if (letter == null) 24.dp else 48.dp)
            .border(1.dp, Color.Black, shape = RoundedCornerShape(12.dp))
            .background(if (letter == null) Color.Transparent else Color.Green),
        contentAlignment = Alignment.Center
    ) {
        Text(letter ?: "", fontSize = 24.sp)
    }
}