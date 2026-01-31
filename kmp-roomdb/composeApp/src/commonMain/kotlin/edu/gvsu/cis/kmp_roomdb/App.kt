package edu.gvsu.cis.kmp_roomdb

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.gvsu.cis.kmp_roomdb.db.AppDAO

@Composable
fun App(dao: AppDAO) {
    MaterialTheme {
        val appVM = viewModel<AppViewModel> {
            AppViewModel(dao)
        }
        DatabaseDemoScreen(modifier = Modifier.windowInsetsPadding(WindowInsets.displayCutout),
            viewModel = appVM)
    }
}