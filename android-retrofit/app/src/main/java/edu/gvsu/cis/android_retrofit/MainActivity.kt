package edu.gvsu.cis.android_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.coroutineScope

class MainActivity : AppCompatActivity() {
    private val VM by viewModels<MyViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myList = findViewById<RecyclerView>(R.id.name_list)
        val progress = findViewById<ProgressBar>(R.id.loading_prrogress)
        progress.visibility = View.VISIBLE
        findViewById<Button>(R.id.fetchBtn).setOnClickListener {
            VM.getNames(10)
        }
        val aButton = findViewById<Button>(R.id.ebird_btn)
        aButton.setOnClickListener {
            VM.getEBirdRegions()
            Snackbar.make(aButton, "Check Your LogCat for Ebird Responses", Snackbar.LENGTH_LONG)
                .show()
        }
        myList.adapter = NameAdapter(this, VM.persons.value!!)
        myList.layoutManager = LinearLayoutManager(this)
        VM.persons.observe(this) {
            myList.adapter?.notifyDataSetChanged()
        }
        VM.downloadComplete.observe(this) { loadingDone ->
            // Hide the progress bar
            if (loadingDone) {
                progress.visibility = View.GONE
            }
        }
    }
}