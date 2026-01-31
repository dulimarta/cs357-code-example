package edu.gvsu.cis.kmp_roomdb

import androidx.compose.ui.window.ComposeUIViewController
import edu.gvsu.cis.kmp_roomdb.db.getDatabaseInstance

fun MainViewController() = ComposeUIViewController {
    val db = getDatabaseInstance(getDatabaseBuilder())
    App(db.appDAO)
}