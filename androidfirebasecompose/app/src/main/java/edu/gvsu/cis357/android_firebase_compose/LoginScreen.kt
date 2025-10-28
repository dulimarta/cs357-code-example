package edu.gvsu.cis357.android_firebase_compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.gvsu.cis357.android_firebase_compose.ui.theme.AndroidfirebasecomposeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    onLoginSuccess: (String) -> Unit
) {
    var username by remember { mutableStateOf("me@test.com") }
    var password by remember { mutableStateOf("1234567") }
    var hasLoginError by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val loginState by authViewModel.loginState.collectAsState()
    LaunchedEffect(hasLoginError) {
        if (hasLoginError) {
            delay(1200L)
            hasLoginError = false
        }
    }
    Column(modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            username,
            isError = hasLoginError,
            onValueChange = { username = it },
            label = { Text("Email") })
        OutlinedTextField(
            password, isError = hasLoginError, onValueChange = { password = it },
            label = { Text("Password") }, visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = {
            scope.launch {
                val whoami = authViewModel.authenticate(username, password)
                if (whoami != null)
                    onLoginSuccess(whoami)
                else
                    hasLoginError = true
            }
        }) {
            Text("Login")
            if (loginState.inProgress)
                CircularProgressIndicator(color = Color.Yellow, modifier = Modifier.size(32.dp))
        }
        if (loginState.error != null)
            Text(loginState.error!!, color = androidx.compose.material3.MaterialTheme.colorScheme.error)
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    AndroidfirebasecomposeTheme {
//        LoginScreen() {
//            println("Logged in as $it")
//        }
    }
}