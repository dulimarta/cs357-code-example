package edu.gvsu.cis.kmpexpectactual

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import platform.UIKit.UIViewController

@Composable
actual fun DualScreen(modifier: Modifier) {
    val factory = LocalSwiftUIViewFactory.current
    Column {
        Text("Dual Screen (iOS)")
        if (factory != null) {
            val uiVC = remember { factory.getSwiftUIView()  }
            UIKitViewController(factory = { uiVC as UIViewController })
        } else {
            Text("Can't show SwiftUI")
        }
    }
}