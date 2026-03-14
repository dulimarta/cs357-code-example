package edu.gvsu.cis.kmpexpectactual

interface DeviceInfo {
    val pid: Int
    val deviceModel: String
}

expect fun getDeviceInfo(): DeviceInfo
expect fun processName(): String

expect object DeviceInfoBuilder {
    fun build(): DeviceInfo
}
