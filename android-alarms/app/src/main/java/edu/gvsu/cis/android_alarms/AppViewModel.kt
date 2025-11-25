package edu.gvsu.cis.android_alarms

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.AndroidViewModel
import kotlin.random.Random

class AppViewModel(val app: Application): AndroidViewModel(app) {
    lateinit var alarmManager: AlarmManager
    lateinit var notificationManager: NotificationManager
    val context = app.applicationContext

    init {
        alarmManager = context.getSystemService<AlarmManager>()!!
        notificationManager = context.getSystemService<NotificationManager>()!!

    }

    fun wakeUpSecondsFromNow(seconds: Int, useBackStack: Boolean) {
        val  recvIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("EXTRA_MESSAGE", "Message from App")
            putExtra("USE_BACK_STACK", if (useBackStack) 1 else 0)
        }
        val alarmIntent = PendingIntent.getBroadcast(context, 0, recvIntent, PendingIntent.FLAG_IMMUTABLE)
        // Schedule inexact alarm
        alarmManager?.set(AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime() + seconds * 1000,
            alarmIntent)
    }

    fun showNotification() {
        val notification = NotificationCompat.Builder(context!!, "NC-gvsu")
            .setContentTitle("Notification Title")
            .setContentText("Sent from ViewModel")
            .setSmallIcon(R.drawable.outline_alarm_24)
            .build()
        notificationManager.notify(Random.nextInt(), notification)

    }
}