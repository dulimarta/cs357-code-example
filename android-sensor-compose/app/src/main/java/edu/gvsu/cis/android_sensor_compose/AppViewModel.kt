package edu.gvsu.cis.android_sensor_compose

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorEventListener2
import android.hardware.SensorManager
import androidx.core.content.getSystemService
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.flow.update

data class GravitySensorData(val x: Float = 0f, val y:Float = 0f, val z:Float = 0f)
class AppViewModel(val app: Application): AndroidViewModel(app) {
    var sensorMgr: SensorManager?

    val gravityListener = object: SensorEventListener {
        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        }

        override fun onSensorChanged(ev: SensorEvent?) {
            println(ev?.values)
            if (ev != null) {
                _gravity.update {
                    it.copy(x = ev.values[0], y = ev.values[1], z = ev.values[2])
                }
            }
        }

    }

    private val _gravity = MutableStateFlow<GravitySensorData>(GravitySensorData())
    val gravity = _gravity.asStateFlow()
        .sample(100)
    init {
        sensorMgr = app.applicationContext.getSystemService<SensorManager>()
        val gravitySensors = sensorMgr?.getSensorList(Sensor.TYPE_GRAVITY)
        gravitySensors?.let {
            if (it.size > 0) {
                sensorMgr?.registerListener(gravityListener, it[0], 5000000)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        sensorMgr?.unregisterListener(gravityListener)
    }

}