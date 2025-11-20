package edu.gvsu.cis.android_audio_compose

import android.content.ComponentName
import android.content.Context
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Row
import androidx.glance.text.Text
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors

class MyAppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = MyAppWidget()
}

class MyAppWidget : GlanceAppWidget() {
    private var mc: MediaController? = null
    override suspend fun provideGlance(context: Context, id: GlanceId) {

        val sessionToken = SessionToken(
            context,
            ComponentName(context, MyAudioService::class.java)
        )
        val mcBuilder = MediaController.Builder(context, sessionToken)
            .buildAsync()
        mcBuilder.addListener({
            mc = mcBuilder.get()
        }, MoreExecutors.directExecutor())
        provideContent {
            // create your AppWidget here
            Row {
                Button("Play", onClick = {
                    mc?.play()
                })
                Button("Stop", onClick = {
                    mc?.stop()
                })
            }
        }
    }
}