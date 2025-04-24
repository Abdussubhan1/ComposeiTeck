package com.example.itecktestingcompose.functions

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.google.firebase.messaging.FirebaseMessagingService
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.itecktestingcompose.R
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        if (remoteMessage.data.isNotEmpty()) {

            val title = remoteMessage.data["title"] ?: "Default Title"
            val body = remoteMessage.data["body"] ?: "Default Body"

            showNotification(title, body)

        }
        Log.d("FCM Message", "Received message: ${remoteMessage.data}")
    }

    @SuppressLint("ServiceCast")
    private fun showNotification(title: String, message: String) {
        val channelId = "my_channel_id"
        val channelName = "My Notification Channel"

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, importance)
        notificationManager.createNotificationChannel(channel)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification) // Make sure you have this icon in drawable
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(0, notificationBuilder.build())
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM_TOKEN", "New token: $token")

        saveTokenLocally(applicationContext, token) //Any new refreshed token will be saved here

//        sendTokenToServer(token)

        //todo CALL API here
    }
}
