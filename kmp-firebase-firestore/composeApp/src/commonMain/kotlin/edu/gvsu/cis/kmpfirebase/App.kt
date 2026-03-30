package edu.gvsu.cis.kmpfirebase

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val vm = viewModel<AppViewModel> {
            AppViewModel()
        }
        FirestoreScreen(modifier = Modifier.safeContentPadding().
        fillMaxSize(), viewModel = vm)
    }
}