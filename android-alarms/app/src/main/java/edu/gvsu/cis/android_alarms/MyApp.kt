package edu.gvsu.cis.android_alarms

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.content.getSystemService

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        val notificationManager = getSystemService<NotificationManager>()
        // Use NC-gvsu for notification anchnel id
        val channel = NotificationChannel("NC-gvsu", "Demo", NotificationManager.IMPORTANCE_HIGH)
        notificationManager?.createNotificationChannel(channel)
    }
}