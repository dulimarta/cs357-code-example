package edu.gvsu.cis.android_alarms

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.getSystemService
import kotlin.random.Random

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(ctx: Context?, z: Intent?) {

        val msg = z?.getStringExtra("EXTRA_MESSAGE") ?: "None"
        val useBackStack = z?.getIntExtra("USE_BACK_STACK", -1) ?: 0

        val launchActivityIntent = Intent(ctx, SecondActivity::class.java)

        val notificationMgr: NotificationManager = ctx?.getSystemService<NotificationManager>()!!
        val nBuilder = NotificationCompat.Builder(ctx!!, "NC-gvsu")
            .setContentTitle("Notification Title")
            .setContentText("From BroadcastReceiver: ${msg}")
            .setSmallIcon(R.drawable.outline_alarm_24)
            .setAutoCancel(true)
        if (useBackStack == 1) {
            val notifPendingIntent = TaskStackBuilder.create(ctx).run {
                addNextIntentWithParentStack(launchActivityIntent)
                getPendingIntent(0, FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT)
            }
            nBuilder.addAction(R.drawable.outline_alarm_24, "Launch Activity", notifPendingIntent)
        }
        else {
            val notifPendingIntent = PendingIntent.getActivity(ctx!!, 0, launchActivityIntent, FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT)
            nBuilder.addAction(R.drawable.outline_alarm_24, "Launch Activity", notifPendingIntent)
        }
        notificationMgr.notify(Random.nextInt(), nBuilder.build())
    }
}