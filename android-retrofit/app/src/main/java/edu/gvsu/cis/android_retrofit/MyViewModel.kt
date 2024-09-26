package edu.gvsu.cis.android_retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class PersonSimplified(val name: String, val email: String, val imageURL: String)
class MyViewModel : ViewModel() {
    // MutableList is Kotlin specific
    // You can replace MutableList below with other Java collection types that work
    private val _persons =  MutableLiveData<MutableList<PersonSimplified>>(mutableListOf())
    private val _downloadComplete = MutableLiveData(false)
    val persons: LiveData<MutableList<PersonSimplified>> get() = _persons
    val downloadComplete: LiveData<Boolean> get() = _downloadComplete
    var apiEndpoint: RandomUserApi
    val eBirdEndPoint: EBirdApi

    init {
        apiEndpoint = RandomUserClient.getInstance().create(RandomUserApi::class.java)
        eBirdEndPoint = EBirdClient.getInstance().create(EBirdApi::class.java)
        getNames(3) // Show the first 3 names
    }

     fun getNames(count:Int) {
        println("Inside ViewModel getNames($count)")
        viewModelScope.launch(Dispatchers.IO){
            val rNames = apiEndpoint.getRandomNames(count)
            rNames.body()?.let {
                println("Info field is ${it.info}")
                _persons.value!!.addAll(it.results.map {
                    PersonSimplified(name = "${it.name.first} ${it.name.last}",
                        email = it.email,
                        imageURL = it.picture.thumbnail)
                })
                // Must use  postValue to update live data from a non-main thread
                delay(2500) // Intentional delay so you can see the progress bar
                _persons.postValue(_persons.value)
                _downloadComplete.postValue(true)
            }
        }
    }

    fun getEBirdRegions() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = eBirdEndPoint.getAdjacentRegionsTo("US-MI")
            if (response.isSuccessful()) {
                response.body()?.let { regions ->
                    println("Region(s) adjacent to UD-MI are")
                    for (r in regions) {
                        println(r)
                    }
                }
            } else {
                println("HTTP Error ${response.code()} ${response.message()}")
            }
        }
    }
}