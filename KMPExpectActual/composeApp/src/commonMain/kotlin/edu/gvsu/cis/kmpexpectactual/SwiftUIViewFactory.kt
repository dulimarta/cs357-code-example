package edu.gvsu.cis.kmpexpectactual

import androidx.compose.runtime.staticCompositionLocalOf

interface SwiftUIViewFactory {
    // Ideally the return type should be UIViewController
    fun getSwiftUIView(): Any
}

val LocalSwiftUIViewFactory = staticCompositionLocalOf<SwiftUIViewFactory?> {  null }