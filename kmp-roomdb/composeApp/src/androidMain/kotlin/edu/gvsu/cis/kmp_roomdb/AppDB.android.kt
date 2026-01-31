package edu.gvsu.cis.kmp_roomdb

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.gvsu.cis.kmp_roomdb.db.AppDB

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<AppDB> {
    val appContext = context.applicationContext
    val dbPath = appContext.getDatabasePath("app_data.db")
    return Room.databaseBuilder<AppDB>(appContext,
        dbPath.absolutePath)
}

