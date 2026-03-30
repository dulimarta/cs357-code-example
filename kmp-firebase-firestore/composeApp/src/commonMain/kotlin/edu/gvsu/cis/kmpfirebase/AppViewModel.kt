package edu.gvsu.cis.kmpfirebase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.QuerySnapshot
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

data class UiState(
    val loading: Boolean = false,
    val uid: String? = null,
    val error: String? = null
)

class AppViewModel : ViewModel() {
    val appDB = Firebase.firestore
    val allCities = appDB.collection("cities")
        .snapshots
        .map {qs: QuerySnapshot ->
            qs.documents.map { ds: DocumentSnapshot ->
                ds.data<City>()
            }
        }

    fun addRandomCity() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val collRef = appDB.collection("cities")
                val randomCity = City(name = randomCities.random(),
                    population = Random.nextInt(50_000, 1_000_000))
                val docRef = collRef.add(randomCity)
                docRef.updateFields {
                    "id" to docRef.id
                }
            } catch (e: Exception) {
                println("Firestore error: ${e.message}")
            }
        }
    }
    fun deleteCityById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val docRef = appDB.document("cities/$id")
            docRef.delete()
        }
    }
}