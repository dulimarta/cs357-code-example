package edu.gvsu.cis357.android_firebase_compose

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import edu.gvsu.cis357.android_firebase_compose.ui.theme.AndroidfirebasecomposeTheme

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = AuthViewModel(),
    onLoginSuccess: (String) -> Unit
) {
    var username by remember { mutableStateOf("me@test.com") }
    var password by remember { mutableStateOf("1q2w3e4r5") }
    var authSuccess = authViewModel.authSuccess.observeAsState()

    Column {
        OutlinedTextField(username,
            isError = authSuccess.value == false,
            onValueChange = { username = it },
            label = { Text("Email") })
        OutlinedTextField(
            password, isError = authSuccess.value == false, onValueChange = { password = it },
            label = { Text("Password") }, visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = {
            authViewModel.authenticate(username, password) {
                if (it != null) {
//                    println("Logged in as $it")
                    onLoginSuccess(it)
                } else {
//                    println("Login failed")
                }
            }
        }) {
            Text("Login")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    AndroidfirebasecomposeTheme {
        LoginScreen() {
            println("Logged in as $it")
        }
    }
}