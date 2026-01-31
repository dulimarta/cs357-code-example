package edu.gvsu.cis.kmp_roomdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.gvsu.cis.kmp_roomdb.db.AppDAO
import edu.gvsu.cis.kmp_roomdb.db.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlin.random.Random

class AppViewModel (val dao: AppDAO) : ViewModel() {
    val everyone = dao.selectAll()

    fun deletePerson(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deletePerson(person)
        }
    }

    fun addName() {
        val p = Person(name ="Person #${Random.nextInt(1000,9999)}",
            age = Random.nextInt(10, 80))
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertPerson(p)
        }
    }
}
