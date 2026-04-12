package edu.gvsu.cis.kmplocation

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await
import org.koin.java.KoinJavaComponent.getKoin

class AndroidLokationProvider(val context: Context): LokationProvider {
    val client = LocationServices.getFusedLocationProviderClient(context)

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override suspend fun getCurrentLocation(): Pair<Double, Double>?  {
        val z = client.lastLocation.await()
        return Pair(z.latitude, z.longitude)
    }
}
actual fun getLokationProvider(): LokationProvider {
    val ctx = getKoin().get<Context>()
    return AndroidLokationProvider(ctx)
}