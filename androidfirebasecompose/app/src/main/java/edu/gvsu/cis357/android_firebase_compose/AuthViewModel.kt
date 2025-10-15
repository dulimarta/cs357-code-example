package edu.gvsu.cis357.android_firebase_compose

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private var _loginError = MutableStateFlow<String?>(null)
    val loginError = _loginError.asStateFlow()

    suspend fun authenticate(email: String, password: String): String? {
        _loginError.value = null
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            return result.user?.uid
        } catch (e: Exception) {
            _loginError.value = e.localizedMessage
            return null
        }
    }
}