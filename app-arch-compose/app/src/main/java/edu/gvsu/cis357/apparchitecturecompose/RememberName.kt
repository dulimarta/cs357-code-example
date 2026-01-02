package edu.gvsu.cis357.apparchitecturecompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RememberName(modifier: Modifier = Modifier) {
    var firstName by remember { mutableStateOf("") }
    var lastName by rememberSaveable() {mutableStateOf("") }
    Column(modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Rotate screen & compare effect")
        TextField(value = firstName, onValueChange = { firstName = it},
            label = {Text("remember")})
        TextField(value = lastName, onValueChange = { lastName = it},
            label = {Text("rememberSaveable")})
    }
}

@Preview (showBackground = true)
@Composable
fun RememberNamePreview() {
    RememberName()
}