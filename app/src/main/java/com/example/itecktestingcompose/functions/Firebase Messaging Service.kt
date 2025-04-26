package com.example.itecktestingcompose.functions


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import com.google.firebase.messaging.FirebaseMessagingService
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.itecktestingcompose.Mainactivity.MainActivity
import com.example.itecktestingcompose.R
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {


        if (remoteMessage.data.isNotEmpty()) {

            val title = remoteMessage.data["title"] ?: "Default Title"
            val body = remoteMessage.data["body"] ?: "Default Body"
            val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            sharedPref.edit().putBoolean("hasNewNotification", true).apply()

            showNotification(title, body)

        }
        Log.d("FCM Message", "Received message: ${remoteMessage.data}")
    }

    //Function to show Notification

    @SuppressLint("ServiceCast")
    fun showNotification(title: String, message: String) {

        val channelId = "my_channel_id"
        val channelName = "My Notification Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val channel = NotificationChannel(channelId, channelName, importance).apply {
            setShowBadge(true)
        }

        notificationManager.createNotificationChannel(channel)

        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.icon)

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.icon)
            .setLargeIcon(largeIcon)// Make sure you have this icon in drawable
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent) //this will open app on notification click
            .setAutoCancel(true)

        val notificationId = System.currentTimeMillis().toInt() //to avoid notification overlapping. any coming notification will be new.

        notificationManager.notify(notificationId, notificationBuilder.build())
    }



    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM_TOKEN", "New token: $token")

        saveTokenLocally(applicationContext, token) //Any new refreshed token will be saved here

    }
}
