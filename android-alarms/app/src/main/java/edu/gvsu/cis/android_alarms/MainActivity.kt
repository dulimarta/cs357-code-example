package edu.gvsu.cis.android_alarms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import edu.gvsu.cis.android_alarms.ui.theme.AndroidalarmsTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            val appVM: AppViewModel by viewModels()
            AndroidalarmsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding),
                        vm = appVM
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Greeting(modifier: Modifier = Modifier, vm: AppViewModel) {
    val notifPermission = rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)
    val activity = LocalActivity.current
    val scope = rememberCoroutineScope()
    LaunchedEffect(notifPermission) {
        if (!notifPermission.hasPermission) {
            notifPermission.launchPermissionRequest()
        }
    }
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            "Send Notification",
            fontSize = 24.sp
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Button(onClick = {
                    vm.wakeUpSecondsFromNow(3)
                    scope.launch {
                        delay(750)
                        // Close this activity, just to demonstrate that the notification
                        // is launched from the broadcast receiver (AlarmReceiver)
                        activity?.finish()
                    }
                }) {
                    Text("Via 3-second alarm")
                }

            }
            item {
                Button(
                    onClick = { vm.showNotification() },
                    enabled = notifPermission.hasPermission
                ) {
                    Text("Now")
                }

            }
        }
    }
}

