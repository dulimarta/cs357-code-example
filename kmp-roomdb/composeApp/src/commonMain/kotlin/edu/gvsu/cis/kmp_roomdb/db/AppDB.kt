package edu.gvsu.cis.kmp_roomdb.db

import androidx.room.ConstructedBy
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDAO {
    @Insert
    suspend fun insertPerson(person: Person)

    @Delete
    suspend fun deletePerson(person: Person)

    @Query("SELECT * FROM person")
    fun selectAll(): Flow<List<Person>>
}

@Database(entities = [Person::class], version = 1)
@ConstructedBy(MyDatabaseBuilder::class)
abstract class AppDB : RoomDatabase() {
    abstract val appDAO: AppDAO
}

expect object MyDatabaseBuilder: RoomDatabaseConstructor<AppDB> {
    override fun initialize(): AppDB
}

fun getDatabaseInstance(builder: RoomDatabase.Builder<AppDB>): AppDB {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}