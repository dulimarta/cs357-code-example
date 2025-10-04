package edu.gvsu.cis357.room

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import edu.gvsu.cis357.room.data.Guest
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class MyViewModel(app: Application) : AndroidViewModel(app) {
    val faker = Faker()
    private val myDB = (app as MyApplication).myDB.getInstance()
    lateinit var guests: Flow<List<Guest>>

    init {
        viewModelScope.launch {
            guests = myDB.getAllGuests()
        }
    }

    fun addNewGuest() {
        viewModelScope.launch(Dispatchers.IO) {
            val x = Guest(0, faker.name.firstName(), faker.name.lastName())
            myDB.insert(x)

        }
    }

    fun deleteById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            myDB.deleteGuest(id)
        }
    }
}