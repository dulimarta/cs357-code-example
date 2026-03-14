package edu.gvsu.cis.kmpexpectactual

import android.os.Build
import android.os.Process
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class AndroidDeviceInfo() : DeviceInfo {
    override val pid = Process.myPid()
    override val deviceModel = Build.MODEL
}

actual fun processName(): String =
    "Android Process ${Process.myPid()} ${android.os.Process.PHONE_UID}"

actual fun getDeviceInfo(): DeviceInfo = AndroidDeviceInfo()

actual object DeviceInfoBuilder {
    private val obj: DeviceInfo by lazy { AndroidDeviceInfo() }
    actual fun build() = obj
}
