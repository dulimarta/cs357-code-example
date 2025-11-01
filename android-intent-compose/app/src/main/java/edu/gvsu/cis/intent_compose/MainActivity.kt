package edu.gvsu.cis.intent_compose

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.gvsu.cis.intent_compose.ui.theme.IntentComposeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val hostState = remember { SnackbarHostState() }
            val appVM: AppViewModel by viewModels()
            LaunchedEffect(SnackbarController.messageQueue) {
                SnackbarController.messageQueue.collect {
                    hostState.showSnackbar(message = it)
                }
            }
            IntentComposeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState) }) { innerPadding ->

                    Column(modifier = Modifier.padding(innerPadding)) {
                        IntentDemo()

                        IntentWithResult(vm = appVM)

                    }
                }
            }
        }
    }
}

@Composable
fun IntentDemo(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickContact()
    ) { }
    Column(
        modifier = modifier.padding(top = 24.dp, start = 8.dp),
    ) {
        Text("Intents withoutt Result")
        LazyVerticalGrid(
            columns = GridCells.Fixed(
                count = 2
            ),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Button(onClick = {
                    val vidCamIntent = Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA)
                    context.startActivity(vidCamIntent)
                }) { Text("Use Camera (Video)") }
            }
            item {
                Button(onClick = {
                    val camIntent = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)
                    context.startActivity(camIntent)
                }) { Text("Use Camera (Photo)") }
            }
            item {
                Button(onClick = {
                    val msgIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "A short message")
                    }
                    context.startActivity(msgIntent)
                }) { Text("Send Text Msg") }
            }
            item {
                Button(onClick = {
                    try {
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            putExtra(
                                Intent.EXTRA_EMAIL,
                                arrayOf("dulimarh@gvsu.edu", "dulimarh@mail.gvsu.edu")
                            )
                        }
                        context.startActivity(emailIntent)
                    } catch (e: ActivityNotFoundException) {
                        scope.launch {
                            SnackbarController.sendMessage(e.localizedMessage)
                        }

                    }
                }) { Text("Send Email") }
            }
            item {
                Button(onClick = {
                    val contentIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
                        type = "image/*"
                    }
                    context.startActivity(contentIntent)
                }) { Text("Browse Images") }
            }
            item {
                Button(onClick = {
                    scope.launch {
                        SnackbarController.sendMessage("Hi hans")
                    }
                }) { Text("Just Snack") }
            }
        }
    }
}

@Composable
fun IntentWithResult(modifier: Modifier = Modifier, vm: AppViewModel) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val imgUri by vm.imageFileUri.collectAsState()
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSaved ->
        if (isSaved) {
//            val thumbnail = result.data.getParcelableExtra<Bitmap>("data")
            scope.launch {
                SnackbarController.sendMessage(("Image is saved to ${imgUri?.path}"))
            }
        } else {
            vm.removeImageFile()
            scope.launch {
                SnackbarController.sendMessage(("Ooooops"))
            }

        }
    }
    LaunchedEffect(imgUri) {
        println("Image uri ${imgUri}")
        if (imgUri != null) {
            cameraLauncher.launch(imgUri!!)
//                    cameraLauncher.launch(captureIntent)

        }
    }
    Column(modifier) {
        Text("Intents with Result")
        LazyVerticalGrid(
            columns = GridCells.Fixed(
                count = 2
            ),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Button(onClick = {
                    vm.setupImageUri("demo")
                }) { Text("Capture Image") }
            }
//            item {
//                Button(onClick = {
//                    val contentIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
//                        type = "application/pdf"
//                    }
//                    context.startActivity(contentIntent)
//                }) { Text("Get PDFs") }
//            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IntentComposeTheme {
        IntentDemo()
    }
}