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

    fun wakeUpSecondsFromNow(msg: String, seconds: Int, useBackStack: Boolean) {
        val  recvIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("EXTRA_MESSAGE", msg)
            putExtra("USE_BACK_STACK", if (useBackStack) 1 else 0)
        }
        val alarmIntent = PendingIntent.getBroadcast(context, 0, recvIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
        // Schedule inexact alarm
        alarmManager?.set(AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime() + seconds * 1000,
            alarmIntent)
    }

    fun showNotification(msg: String) {
        val notification = NotificationCompat.Builder(context!!, "NC-gvsu")
            .setContentTitle("Notification Title")
            .setContentText(msg)
            .setSmallIcon(R.drawable.outline_alarm_24)
            .build()
        notificationManager.notify(Random.nextInt(), notification)

    }
}