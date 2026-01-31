package edu.gvsu.cis.kmp_roomdb

import androidx.room.Room
import androidx.room.RoomDatabase
import edu.gvsu.cis.kmp_roomdb.db.AppDB
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDB> {
    val dbPath = documentDirectory() + "/app_data.db"
    println("Database path: $dbPath")
    return Room.databaseBuilder<AppDB>(dbPath)
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = true,
        error = null)
    return requireNotNull(documentDirectory?.path)
}