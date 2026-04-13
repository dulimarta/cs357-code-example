package edu.gvsu.cis.kmplocation

import android.Manifest
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.java.KoinJavaComponent.getKoin
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine

class AndroidLokationProvider(val context: Context): LokationProvider {
    private val client = LocationServices.getFusedLocationProviderClient(context)
    private var resultContinuation: Continuation<Pair<Double, Double>>? = null

    private val lokationListener = object : LocationListener {
        override fun onLocationChanged(p0: Location) {
            println("New location: ${p0.latitude}, ${p0.longitude}")
            resultContinuation?.resumeWith(Result.success(Pair(p0.latitude, p0.longitude)))
            client.removeLocationUpdates(this)
            resultContinuation = null
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override suspend fun getCurrentLocation(): Pair<Double, Double>? =
        suspendCoroutine {
            resultContinuation = it
            val req = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
                .build()
            client.requestLocationUpdates(req, lokationListener, Looper.getMainLooper())

        }
}

actual fun getLokationProvider(): LokationProvider {
    val ctx = getKoin().get<Context>()
    return AndroidLokationProvider(ctx)
}