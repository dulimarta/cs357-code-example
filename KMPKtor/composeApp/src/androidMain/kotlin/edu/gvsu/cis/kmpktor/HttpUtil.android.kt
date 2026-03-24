package edu.gvsu.cis.kmpktor

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android

//import io.ktor.client.engine.okhttp.OkHttp
//import io.ktor.client.engine.okhttp.OkHttpEngine

actual fun getHttpEngine(): HttpClientEngine = Android.create()
