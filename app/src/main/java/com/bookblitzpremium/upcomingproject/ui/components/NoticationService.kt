package com.bookblitzpremium.upcomingproject.ui.components

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.bookblitzpremium.upcomingproject.MainActivity
import com.bookblitzpremium.upcomingproject.R
import dagger.hilt.android.qualifiers.ApplicationContext


const val NOTICATION_CHANNEL_ID = "ch-1"
const val NOTICATION_CHANNEL_NAME = "test Notication"
const val NOTICATION_ID = 100
const val REQUEST_CODE = 200


class NotificationService(
    @ApplicationContext private val context: Context
) {
    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val myIntent = Intent(context, MainActivity::class.java)

    private val pendingIntent: PendingIntent = PendingIntent.getActivity(
        context,
        REQUEST_CODE,
        myIntent,
        PendingIntent.FLAG_IMMUTABLE
    )

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Default Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for default notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(title:String, content: String = "") {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_logo)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // Dismiss notification when tapped
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val REQUEST_CODE = 0
        private const val NOTIFICATION_CHANNEL_ID = "default_channel_id"
        private const val NOTIFICATION_ID = 1
    }
}


//add the custom notication style