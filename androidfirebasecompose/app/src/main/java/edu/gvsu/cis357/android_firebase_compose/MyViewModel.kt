package edu.gvsu.cis357.android_firebase_compose

//import com.google.firebase.firestore.firestore
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import edu.gvsu.cis357.android_firebase_compose.data.Student
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MyViewModel : ViewModel() {
    var _allNames = MutableStateFlow<List<Student>>(emptyList())
    val allNames = _allNames.asStateFlow()
    private var _latestDocID = MutableStateFlow<String?>(null)
    val lastDocId = _latestDocID.asStateFlow()

    val myDB = Firebase.firestore
    lateinit var registration: ListenerRegistration
    val memberCollection = myDB.collection("members")
    override fun onCleared() {
        super.onCleared()
        this.registration.remove()
    }

    val faker = Faker()

    init {
//        fetchMembers()
        this.registration = memberCollection.addSnapshotListener { qs, err ->
            if (qs != null) {
                for (chg in qs.documentChanges) {
                    val z = chg.document.toObject<Student>()
                    print("${z.firstName} is ${chg.type}")
                    if (chg.type == DocumentChange.Type.ADDED) {
                        _allNames.update {
                            it + z
                        }
                    }
                    else if (chg.type == DocumentChange.Type.REMOVED) {
                        _allNames.update {
                            it.filter { s ->
                                s.id != chg.document.id
                            }
                        }
                    }
                }
            }
        }
    }

    fun fetchMembers() {
        viewModelScope.launch(Dispatchers.IO) {
            val qs = memberCollection.get().await()
            val students = mutableStateListOf<Student>()
            qs.documents.forEach {
                it.toObject<Student>()?.let { student ->
                    student.id = it.id // Save Firestore DocID
                    students.add(student)
                }
            }
            _allNames.value = students
        }
    }

    fun deleteItem(sid: String) {
//        _allNames.update {
//            it.filter { it.id != sid }
//        }
        viewModelScope.launch(Dispatchers.IO) {
            memberCollection.document(sid).delete().await()
        }

    }

    fun addRandomName() {
        val first = faker.name.firstName()
        val last = faker.name.lastName()
        val newStudent = Student(firstName = first, lastName = last)
        viewModelScope.launch(Dispatchers.IO) {
            val z = memberCollection.add(newStudent).await()
            _latestDocID.value = z.id
//            _allNames.update {
//                it + newStudent
//            }
        }
    }

    fun sortByFirstName() {
        _allNames.update {
            it.sortedBy { it.firstName }
        }
    }

    fun sortByLastName() {
        _allNames.update {
            it.sortedBy { it.lastName }
        }
    }
}