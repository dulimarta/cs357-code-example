package edu.gvsu.cis357.room.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity
data class Guest(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val firstName: String,
    val lastName: String
)

@Entity
data class PartyTable(
    @PrimaryKey(autoGenerate = true) val tid: Int,
    val seats: Int = 3
)

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(p: Guest)

    @Insert
    suspend fun insert(t: PartyTable)

    @Delete
    suspend fun deleteTable(t: PartyTable)

    @Update
    suspend fun modifyGuest(g: Guest)

    @Query("DELETE FROM guest WHERE id = :id")
    suspend fun deleteGuest(id: Int)

    @Query("SELECT * FROM guest WHERE id = :id")
    suspend fun getGuest(id: Int): Guest

    // Do not declare suspend when returning a Flow
    @Query("SELECT * FROM guest")
    fun getAllGuests(): Flow<List<Guest>>

    @Query("SELECT * FROM guest ORDER BY :field ASC")
    fun getAllGuestsSortedBy(field: String): Flow<List<Guest>>
}

@Database(
    entities = [Guest::class, PartyTable::class], version = 1, exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getInstance(): PersonDao
}