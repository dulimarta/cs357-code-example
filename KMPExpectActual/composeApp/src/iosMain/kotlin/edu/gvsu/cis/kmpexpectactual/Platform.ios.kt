package edu.gvsu.cis.kmpexpectactual

import platform.UIKit.UIDevice
import platform.Foundation.NSProcessInfo
class IOSDeviceInfo: DeviceInfo {
    override val pid = NSProcessInfo.processInfo.processIdentifier
    override val deviceModel = UIDevice.currentDevice.localizedModel
}

actual fun processName(): String =
    "iOS Process-${NSProcessInfo.processInfo.processIdentifier}"

actual fun getDeviceInfo(): DeviceInfo = IOSDeviceInfo()

actual object DeviceInfoBuilder {
    actual fun build(): DeviceInfo = IOSDeviceInfo()
}