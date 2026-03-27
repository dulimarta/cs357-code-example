package edu.gvsu.cis.kmpfirebase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.AuthCredential
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.GoogleAuthProvider
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val loading: Boolean = false,
    val uid: String? = null,
    val error: String? = null
)

class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()
    val appAuth = Firebase.auth

    fun login(email: String, password: String) {
        _uiState.update {
            it.copy(loading = true, uid = null, error = null)
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val res = appAuth.signInWithEmailAndPassword(email, password)
                if (res.user != null) {
                    _uiState.update {
                        it.copy(loading = false, uid = res.user!!.uid)
                    }
                } else {
                    _uiState.update {
                        it.copy(loading = false, error = "Unable to authenticate")
                    }
                    delay(3000)
                    _uiState.update {
                        it.copy(error = null)
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(loading = false, error = e.message)
                }
                delay(3000)
                _uiState.update {
                    it.copy(error = null)
                }
            }
        }
    }

    fun newAccount(email: String, password: String) {
        _uiState.update {
            it.copy(loading = true, uid = null, error = null)
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val res = appAuth.createUserWithEmailAndPassword(email, password)
                if (res.user != null) {
                    _uiState.update {
                        it.copy(loading = false, uid = res.user!!.uid)
                    }
                } else {
                    _uiState.update {
                        it.copy(loading = false, error = "Unable to create account")
                    }
                    delay(3000)
                    _uiState.update {
                        it.copy(error = null)
                    }
                }
            }
            catch (e: Exception) {
                _uiState.update {
                    it.copy(loading = false, error = e.message)
                }
                delay(3000)
                _uiState.update {
                    it.copy(error = null)
                }
            }
        }
    }


}