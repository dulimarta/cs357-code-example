package edu.gvsu.cis357.android_firebase_compose

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await

data class LoginState(
    val error: String? = null,
    val inProgress: Boolean = false
)
class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private var _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    suspend fun authenticate(email: String, password: String): String? {
        _loginState.update { it.copy(inProgress = true, error = null) }
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            delay(1500L)
            _loginState.update { it.copy(inProgress = false) }
            return result.user?.uid
        } catch (e: Exception) {
            _loginState.update {
                it.copy(e.localizedMessage, inProgress = false)
            }
            return null
        }
    }
}