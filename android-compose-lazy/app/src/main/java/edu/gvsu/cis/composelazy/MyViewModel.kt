package edu.gvsu.cis.composelazy

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.serpro69.kfaker.Faker

class MyViewModel: ViewModel() {
    val names = mutableStateListOf<String>()
    val faker = Faker()

    fun randomNames() = (1..20).map {
        "${faker.name.firstName()} ${faker.name.lastName()}"
    }

    init {
        names.addAll(randomNames())
    }
    fun deleteItem(pos:Int) {
        if (pos < names.size) {
            names.removeAt(pos)
        }
    }
}