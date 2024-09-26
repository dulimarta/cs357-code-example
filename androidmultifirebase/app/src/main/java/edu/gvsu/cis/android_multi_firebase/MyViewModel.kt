package edu.gvsu.cis.android_multi_firebase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.FirebaseOptions
import com.google.firebase.app
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.initialize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(app: Application): AndroidViewModel(app) {
    val opt1 = FirebaseOptions.Builder()
        .setProjectId("cs357-demo")
        .setApiKey("AIzaSyAfaU5nB-pcsbapQ8AQdn-rzld5Y95OR3g")
        .setApplicationId("1:520110768371:android:1b353ce4f01489fb8f7786")
        .build()
    var firstDB:FirebaseFirestore
    init {
        val ctx = getApplication<Application>().applicationContext
        Firebase.initialize(ctx, opt1, "Oxygen")
        firstDB = Firebase.firestore(Firebase.app("Oxygen"))
    }

    fun doNothing() {
        println("Firebase Firestore Details $firstDB")
        viewModelScope.launch(Dispatchers.IO) {
            firstDB.collection("members").get()
                .addOnSuccessListener {
                    for (doc in it.documents) {
                        println("Found document ${doc.id}")
                    }

                }
        }
    }
}