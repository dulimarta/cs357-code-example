package edu.gvsu.cis357.retrofit_compose

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.create

class MyViewModel: ViewModel() {
    var apiEndpoint: RandomUserApi
    val users = mutableStateListOf<Person>()

    init {
        println("MyViewModel created")
        apiEndpoint = RandomUserClient.getInstance().create(RandomUserApi::class.java)
    }

    fun getUsers(count:Int = 5) {
        viewModelScope.launch(Dispatchers.IO) {
            val rNames = apiEndpoint.getRandomNames(count)
            rNames.body()?.let {
                it.results?.forEach {p: Person ->
                    users.add(p)
                }
            }
        }
    }

//    fun getPhotos() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val photoResp = pexEndpoint.photoSearch("people")
//            photoResp.body()?.let {
//                println("Number of photos ${it.photos.size}")
//            }
//        }
//    }
    override fun onCleared() {
        super.onCleared()
        println("MyViewModel cleared")
    }
}