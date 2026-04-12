package edu.gvsu.cis.kmplocation

import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationCoordinate2D
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorized
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.Foundation.NSError
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@OptIn(ExperimentalForeignApi::class)
fun CValue<CLLocationCoordinate2D>.toLat(): Double = useContents {
    latitude
}


@OptIn(ExperimentalForeignApi::class)
fun CValue<CLLocationCoordinate2D>.toLng(): Double = useContents {
    longitude
}

private class IosLokationManager : NSObject(), CLLocationManagerDelegateProtocol {
    private val mgr = CLLocationManager()
    private var resultContinuation: CancellableContinuation<Result<CLLocation>>? = null

    init {
        mgr.delegate = this
        mgr.requestWhenInUseAuthorization()
    }

    suspend fun requestCurrentLocation(): Result<CLLocation> = suspendCancellableCoroutine { cont ->
        // keep a reference of the SuspendableCoroutine
        resultContinuation = cont
        mgr.requestLocation()
    }

    override fun locationManager(
        manager: CLLocationManager,
        didChangeAuthorizationStatus: CLAuthorizationStatus
    ) {
        when (didChangeAuthorizationStatus) {
            kCLAuthorizationStatusAuthorizedAlways ->
                mgr.requestLocation()
            kCLAuthorizationStatusAuthorized ->
                mgr.requestLocation()
            kCLAuthorizationStatusAuthorizedWhenInUse ->
                mgr.requestLocation()
            kCLAuthorizationStatusDenied ->
                resultContinuation = null
        }
    }

    override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
        resultContinuation?.let {
            if (it.isActive) {
                // Throw an exception
                it.resumeWithException(Exception(message = didFailWithError.localizedDescription))
                resultContinuation = null
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun locationManager(
        manager: CLLocationManager,
        didUpdateToLocation: CLLocation,
        fromLocation: CLLocation
    ) {
        resultContinuation?.let {
            if (it.isActive) {
                val out = Result.success(didUpdateToLocation)
                it.resume(out)
            }
            resultContinuation = null
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        val loka: CLLocation? = didUpdateLocations.firstOrNull() as CLLocation?
        if (loka != null) {
            resultContinuation?.let {
                if (it.isActive) {
                    val out = Result.success(loka)
                    it.resume(out)
                }
                resultContinuation = null
            }
        } else {
            resultContinuation = null
        }
    }

}

class IosLokationProvider : LokationProvider {
    private val mgr = IosLokationManager()

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun getCurrentLocation(): Pair<Double, Double>? {
        val locRequest = mgr.requestCurrentLocation()
        if (locRequest.isSuccess) {
            val out = locRequest.getOrNull()
            if (out != null)
                return Pair(out.coordinate.toLat(), out.coordinate.toLng())
            else return null
        } else return null
    }
}

actual fun getLokationProvider(): LokationProvider = IosLokationProvider()