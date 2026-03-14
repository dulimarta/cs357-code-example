package edu.gvsu.cis.kmp_roomdb

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        DatabaseDemoScreen(modifier = Modifier.windowInsetsPadding(WindowInsets.displayCutout),
            viewModel = koinViewModel()
        )
    }
}