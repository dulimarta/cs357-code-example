package edu.gvsu.cis.kmplocation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import dev.icerock.moko.permissions.location.LOCATION
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinAppDeclaration

@Composable
@Preview
fun App(modifier: Modifier = Modifier, koinDeclaration: KoinAppDeclaration? = null) {
    KoinApplication(application = {
        koinDeclaration?.invoke(this)
    }) {
        val vm = viewModel {
            AppViewModel()
        }
        MaterialTheme {
            LocationScreen(modifier = Modifier.safeContentPadding(), viewModel = vm)
        }
    }
}

@Composable
fun LocationScreen(modifier: Modifier = Modifier, viewModel: AppViewModel) {
    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController = remember(factory) {
        factory.createPermissionsController()
    }
    val scope = rememberCoroutineScope()
    var hasPermission by remember { mutableStateOf(false) }
    val lok by viewModel.location.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    BindEffect(permissionsController = controller)
    LaunchedEffect(lifecycleOwner) {
        hasPermission = controller.isPermissionGranted(Permission.LOCATION)
    }
    Column(modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            Button(onClick = {
                scope.launch {
                    controller.providePermission(
                        permission = dev.icerock.moko.permissions.Permission.LOCATION,
                    )
                }
            })
            {
                Text("Request Location Permission")
            }
            Text(if (hasPermission) "Granted" else "Unknown")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            Button(onClick = {
                viewModel.getCurrentLocation()
            }) {
                Text("Get Location")
            }
            lok?.let {
                Text("${it.first.toString()} ${it.second.toString()}")
            }
        }
    }
}