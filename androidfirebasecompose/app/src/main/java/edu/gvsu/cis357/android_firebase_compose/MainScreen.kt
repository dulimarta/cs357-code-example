package edu.gvsu.cis357.android_firebase_compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(whoamI: String) {
    Text("Your Firebase UID is $whoamI",
        fontSize = 16.sp,
        modifier = Modifier.padding(16.dp))
}