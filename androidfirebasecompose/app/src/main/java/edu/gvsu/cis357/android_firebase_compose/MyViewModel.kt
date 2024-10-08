package edu.gvsu.cis357.android_firebase_compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
//import com.google.firebase.firestore.firestore
import edu.gvsu.cis357.android_firebase_compose.data.Student
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MyViewModel : ViewModel() {
    var allNames = mutableStateListOf<Student>()
    var latestDocID by mutableStateOf<String?>(null)
        private set

    val myDB = Firebase.firestore
    val memberCollection = myDB.collection("members")
    val faker = Faker()

    init {
        fetchMembers()
    }

    fun fetchMembers() {
        viewModelScope.launch(Dispatchers.IO) {
            memberCollection.get().addOnSuccessListener {
                it.documents.forEach {
                    it.toObject(Student::class.java)?.let { student ->
                        student.id = it.id // Save Firestore DocID
                        allNames.add(student)
                    }
                }
            }
            // Or use await() in place of listeners
//            val out = memberCollection.get().await().map {
//                it.toObject(Student::class.java).also { student ->
//                    student.id = it.id
//                }
//            }
//            allNames.addAll(out.toMutableList())
        }

    }

    fun deleteItem(pos: Int) {
        if (pos < allNames.size) {
            val victim = allNames.removeAt(pos)
            viewModelScope.launch(Dispatchers.IO) {
                memberCollection.document(victim.id).delete().await()
            }
        }
    }

    fun addRandomName() {
        val first = faker.name.firstName()
        val last = faker.name.lastName()
        val newStudent = Student(firstName = first, lastName = last)
        viewModelScope.launch(Dispatchers.IO) {
            val z = memberCollection.add(newStudent).await()
            latestDocID = z.id
            allNames.add(newStudent)
        }

    }
}