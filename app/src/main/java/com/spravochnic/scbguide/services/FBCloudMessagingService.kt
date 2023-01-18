package com.spravochnic.scbguide.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.spravochnic.scbguide.MainActivity
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.repositories.source.SharedPreferencesHelper
import com.spravochnic.scbguide.utils.enums.PushType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FBCloudMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sharedPreferencesHelper.fcmToken = token
    }

    override fun onMessageReceived(message: RemoteMessage) {
        showNotification(message.data, message.notification, message.sentTime)
    }

    private fun showNotification(
        data: Map<String, String>,
        notification: RemoteMessage.Notification?,
        sentTime: Long
    ) {
        val title = notification?.title
        val text = notification?.body
        val pushType = when (data[KEY_PUSH_TYPE]) {
            PushType.MESSAGE.name -> PushType.MESSAGE
            else -> PushType.MESSAGE
        }
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtras(bundleOf(*data.toList().toTypedArray()))
        }
        val pendingIntent =
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        val builder = NotificationCompat.Builder(this, pushType.name).apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle(title)
            setContentText(text)
            priority = NotificationCompat.PRIORITY_HIGH
            setOnlyAlertOnce(true)
            setAutoCancel(true)
            setContentIntent(pendingIntent)
            setChannelId(pushType.name)
        }
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                pushType.name,
                NOTIFICATION_CHANNEL,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(sentTime.toInt(), builder.build())
    }

    companion object {
        const val NOTIFICATION_CHANNEL = "NOTIFICATION_CHANNEL"
        const val KEY_PUSH_TYPE = "pushType"
    }
}