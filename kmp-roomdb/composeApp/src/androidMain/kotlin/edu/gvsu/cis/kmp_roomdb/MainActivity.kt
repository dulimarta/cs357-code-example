package edu.gvsu.cis.kmp_roomdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import edu.gvsu.cis.kmp_roomdb.db.getDatabaseInstance

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val db = getDatabaseInstance(getDatabaseBuilder(applicationContext))
        setContent {
            App(db.appDAO)
        }
    }
}

//@Preview
//@Composable
//fun AppAndroidPreview() {
//
//    App()
//}