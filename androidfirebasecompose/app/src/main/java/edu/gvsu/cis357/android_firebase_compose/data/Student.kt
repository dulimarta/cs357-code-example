package edu.gvsu.cis357.android_firebase_compose.data

import com.google.firebase.firestore.DocumentId

data class Student(
    @DocumentId var id: String = "", val firstName: String = "", val lastName: String = "")
