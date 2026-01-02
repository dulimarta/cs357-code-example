package edu.gvsu.cis357.apparchitecturecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import edu.gvsu.cis357.apparchitecturecompose.ui.theme.AppArchitectureComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val myViewModel by viewModels<TicketViewModel>()
        setContent {
            AppArchitectureComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    TicketScreen(Modifier.padding(innerPadding),
//                        myViewModel)
//                    TicketScreenByCategory(Modifier.padding(innerPadding),
//                        myViewModel)
                    RememberName(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

