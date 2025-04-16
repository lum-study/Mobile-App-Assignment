package com.bookblitzpremium.upcomingproject


import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.bookblitzpremium.upcomingproject.ui.components.NOTICATION_CHANNEL_ID
import com.bookblitzpremium.upcomingproject.ui.components.NOTICATION_CHANNEL_NAME
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication :Application(){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        val notificationManager : NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannel = NotificationChannel(
            NOTICATION_CHANNEL_ID,
            NOTICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH,

            )

        notificationManager.createNotificationChannel(notificationChannel)
    }
}
