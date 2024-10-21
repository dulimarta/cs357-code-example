package edu.gvsu.cis357.room

import android.app.Application
import androidx.room.Room
import edu.gvsu.cis357.room.data.MyDatabase

class MyApplication:Application() {
    lateinit var myDB: MyDatabase
    override fun onCreate() {
        super.onCreate()
        myDB = Room.databaseBuilder(applicationContext, MyDatabase::class.java, "my-db")
            .build()
    }
}