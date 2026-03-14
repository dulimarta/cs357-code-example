package di

import android.content.Context
import androidx.room.Room
import edu.gvsu.cis.kmp_roomdb.db.AppDB
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformDBModule(): Module =
    module {
        single {
            val appContext: Context = androidContext()
            val dbPath = appContext.getDatabasePath("app_data.db")
            Room.databaseBuilder<AppDB>(
                appContext,
                dbPath.absolutePath
            )
        }
    }