package edu.gvsu.cis.intent_compose

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import edu.gvsu.cis.intent_compose.ui.theme.IntentComposeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.contracts.contract

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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun IntentDemo(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val phonePermissionState = rememberPermissionState(Manifest.permission.CALL_PHONE)
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
                val text = if (phonePermissionState.status.isGranted) "Call Phone" else "Check Phone Perm"
                Button(onClick = {
                    if (phonePermissionState.status.isGranted) {
                        val callIntent = Intent(Intent.ACTION_CALL).apply {
                            data = Uri.parse("tel:616-331-9999")
                        }
                        context.startActivity(callIntent)
                    } else {
                        phonePermissionState.launchPermissionRequest()
                    }
                }) {
                    Text(text)
                }
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
                    val mapIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("geo:42.96457783629307, -85.89134140766372")
                    }
                    context.startActivity(mapIntent)
                }) {
                    Text("Show Map")
                }
            }
            item {
                Button(onClick = {
                    val gMapIntent = Intent(Intent.ACTION_VIEW).apply {
                        `package` = "com.google.android.apps.maps"
                        data = Uri.parse("geo:42.96457783629307, -85.89134140766372")

                    }
                    context.startActivity(gMapIntent)
                }) {
                    Text("Google Map")
                }
            }
            item {
                Button(onClick = {
                    val webIntent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                        putExtra(SearchManager.QUERY, "GVSU")
                    }
                    context.startActivity(webIntent)
                }) { Text("Web Search") }

            }
            item {
                Button(onClick = {
                    val webIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://dulimarta-teaching.netlify.app")
                    }
                    context.startActivity(webIntent)
                }) { Text("Web Visit") }

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
    var captureUri by remember { mutableStateOf<Uri?>(null) }
    val arContract = ActivityResultContracts.StartActivityForResult()
    val cameraLauncher = rememberLauncherForActivityResult(arContract) {result ->
        if (result.resultCode == RESULT_OK) {
            captureUri = imgUri
            println("Image at ${captureUri}")
        } else {
            vm.removeImageFile()
            scope.launch {
                SnackbarController.sendMessage("No image is saved")
            }
        }
    }

    val fileSearchLauncher = rememberLauncherForActivityResult(arContract) {result ->
        var msg = ""
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.let {
                val c = context.contentResolver.query(it,
                    arrayOf(OpenableColumns.DISPLAY_NAME),
                    null, null, null)
                c?.moveToFirst()
                val name = c?.getString(0)
                c?.close()
                print("File name ${name}")
                msg = "Selecte file $name"
            }
        } else {
            msg = "No file selected"
        }
        scope.launch {
            SnackbarController.sendMessage(msg)
        }
    }
    LaunchedEffect(captureUri) {
        if (captureUri != null) {
            delay(3000)
            captureUri = null
        }
    }
    Column(modifier = modifier.padding(start = 8.dp)) {
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
                    val fileIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
                        type = "application/pdf"
                    }
                    fileSearchLauncher.launch(fileIntent)
                }) {
                    Text("Open PDF")
                }
            }
            item {
                Button(onClick = {
                    val fileUri = vm.setupImageUri()
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                        putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                    }
                    cameraLauncher.launch(cameraIntent)
                }) { Text("Capture Image") }
            }
        }
        if (captureUri != null) {
            AsyncImage(model = captureUri, contentDescription = null)
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