package edu.gvsu.cis.android_alarms

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import kotlin.random.Random

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(ctx: Context?, z: Intent?) {

        val msg = z?.getStringExtra("EXTRA_MESSAGE") ?: "None"
        val lauchActivityIntent = Intent(ctx, SecondActivity::class.java)
        val notifPendingIntent = PendingIntent.getActivity(ctx, 0, lauchActivityIntent, FLAG_IMMUTABLE)
        val notificationMgr: NotificationManager = ctx?.getSystemService<NotificationManager>()!!
        val notification = NotificationCompat.Builder(ctx!!, "NC-gvsu")
            .setContentTitle("Notification Title")
            .setContentText("From BroadcastReceiver: ${msg}")
            .setSmallIcon(R.drawable.outline_alarm_24)
            .addAction(R.drawable.outline_alarm_24, "Launch Activity", notifPendingIntent)
            .setAutoCancel(true)
            .build()
        notificationMgr.notify(Random.nextInt(), notification)
    }
}