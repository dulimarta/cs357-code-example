package edu.gvsu.cis.kmpexpectactual

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController(factory: SwiftUIViewFactory) =
    ComposeUIViewController {
        CompositionLocalProvider(LocalSwiftUIViewFactory provides factory) {
            App(IOSDeviceInfo())
        }
    }
