package di

import androidx.room.Room
import edu.gvsu.cis.kmp_roomdb.db.AppDB
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual fun platformDBModule(): Module =
    module {
        single {
            val dbPath = documentDirectory() + "/app_data.db"
            println("Database path: $dbPath")
             Room.databaseBuilder<AppDB>(dbPath)
        }
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