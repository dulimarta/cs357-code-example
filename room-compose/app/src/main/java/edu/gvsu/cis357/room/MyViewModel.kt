package edu.gvsu.cis357.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.gvsu.cis357.room.data.Guest
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MyViewModel(app: Application) : AndroidViewModel(app) {
    val faker = Faker()
    private val myDB = (app as MyApplication).myDB.getInstance()
    // Use mutable flow so we can manipulate it internally
    private var _guests = MutableStateFlow<List<Guest>>(emptyList())
    val guests = _guests.asStateFlow()

    init {
        viewModelScope.launch {
            // "Collect" is a DB update listener/observer
            myDB.getAllGuests().collect {
                _guests.value = it
            }
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

    fun sortByFirstName() {
        viewModelScope.launch(Dispatchers.IO) {
//            delay(1000) // To simulate "heavy work"
            _guests.update {
                it.sortedBy { it.firstName }
            }
        }
    }

    fun sortByLastName() {
        viewModelScope.launch(Dispatchers.IO) {
//            delay(1000)
            _guests.update {
                it.sortedBy { it.lastName }
            }
        }
    }
}