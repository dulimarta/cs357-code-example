package edu.gvsu.cis.kmpaudiocontroller

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import dev.icerock.moko.permissions.microphone.RECORD_AUDIO
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.dsl.KoinAppDeclaration

@Composable
@Preview
fun App(modifier: Modifier = Modifier, koinDecl: KoinAppDeclaration? = null) {
    KoinApplication(application = {
        koinDecl?.invoke(this)
        modules(appModule)
    }) {
        val vm: AppViewModel = koinViewModel()

        MaterialTheme {
            AudioScreen(modifier = modifier, viewModel = vm)
        }
    }
}

@Composable
fun AudioScreen(modifier: Modifier = Modifier, viewModel: AppViewModel) {
    val permissionFactory = rememberPermissionsControllerFactory()
    val permissionController = remember(permissionFactory) {
        permissionFactory.createPermissionsController()
    }
    var hasPermission by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current
    BindEffect(permissionController)
    LaunchedEffect(lifecycleOwner) {
        try {
            permissionController.providePermission(Permission.RECORD_AUDIO)
            hasPermission = true
        } catch (e: Exception) {
            when (e) {
                is DeniedException, is DeniedAlwaysException -> {
                    hasPermission = false
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Record:")
            Button(onClick = { viewModel.startRecording() }) {
                Text("Start")
            }
            Button(onClick = { viewModel.stopRecording() }) {
                Text("Stop")
            }
            Button(onClick = { viewModel.playbackRecording() }) {
                Text("Play")
            }
        }
        Text("Permission $hasPermission")
        Button(onClick = {
            permissionController.openAppSettings()
        }) {
            Text("Permission Settings")
        }
    }

}