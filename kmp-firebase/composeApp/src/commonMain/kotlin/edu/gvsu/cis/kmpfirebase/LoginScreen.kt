package edu.gvsu.cis.kmpfirebase

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen(modifier: Modifier = Modifier, viewModel: AppViewModel) {
    val email = rememberTextFieldState()
    val password = rememberTextFieldState()
    val uiState by viewModel.uiState.collectAsState()
    Column(modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(email, label = { Text("Email") }  )
        SecureTextField(password, label = { Text("Password") })


        if (uiState.loading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        Row {
            Button(onClick = {
                viewModel.login(email.text.toString(), password.text.toString())
            }, enabled = email.text.isNotEmpty() && password.text.isNotEmpty()) {
                Text("Login")
            }
            Button(onClick = {
                viewModel.newAccount(email.text.toString(), password.text.toString())
            }, enabled = email.text.isNotEmpty() && password.text.isNotEmpty()) {
                Text("Register")
            }
        }
        if (uiState.error != null) {
            Text("Authentication error: ${uiState.error}")
        }
        if (uiState.uid != null) {
            Text("Your UID ${uiState.uid}")
        }
    }
}