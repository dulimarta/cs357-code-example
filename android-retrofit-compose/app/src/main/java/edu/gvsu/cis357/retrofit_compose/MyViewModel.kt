package edu.gvsu.cis357.retrofit_compose

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel: ViewModel() {
    var apiEndpoint: RandomUserApi
    val users = mutableStateListOf<Person>()

    init {
        println("MyViewModel created")
        apiEndpoint = RandomUserClient.getInstance().create(RandomUserApi::class.java)

    }

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val rNames = apiEndpoint.getRandomNames(25)
            rNames.body()?.results?.forEach {
                users.add(it)
            }
        }

    }
    override fun onCleared() {
        super.onCleared()
        println("MyViewModel cleared")
    }
}