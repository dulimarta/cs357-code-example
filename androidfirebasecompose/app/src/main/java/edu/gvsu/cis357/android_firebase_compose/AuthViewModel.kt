package edu.gvsu.cis357.android_firebase_compose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _authSuccess = MutableLiveData<Boolean>()
    val authSuccess: LiveData<Boolean?> get() = _authSuccess

    fun authenticate(email:String, password:String, onAuthenticated: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {

                    _authSuccess.postValue(true)
                    onAuthenticated(it.user!!.uid)
                }
                .addOnFailureListener {
                    println("Cannot login ${it.message}")
                    _authSuccess.postValue(false)
                }
        }
    }

}