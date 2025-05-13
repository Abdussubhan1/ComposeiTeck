package com.example.itecktestingcompose.functions

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.LocationManager
import com.google.firebase.messaging.FirebaseMessagingService
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.itecktestingcompose.APIFunctions.sendTechnicalLocation
import com.example.itecktestingcompose.Mainactivity.MainActivity
import com.example.itecktestingcompose.R
import com.google.android.gms.location.LocationServices
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.itecktestingcompose.appPrefs.PreferenceManager


class MyFirebaseMessagingService : FirebaseMessagingService() {
    @SuppressLint("ServiceCast")
    val prefs = PreferenceManager(this)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {


        if (remoteMessage.data.isNotEmpty() && remoteMessage.data["title"] != "Location Update") {

            val title = remoteMessage.data["title"] ?: "Default Title"
            val body = remoteMessage.data["body"] ?: "Default Body"
            prefs.setHasNewNotification(value = true)

            showNotification(title, body)

        }

        if (remoteMessage.data.isNotEmpty() && remoteMessage.data["title"] == "Location Update") {

            val fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(applicationContext)



            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isLocationEnabled =
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && isLocationEnabled
            ) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        prefs.setLatitude(latitude = it.latitude.toString())
                        prefs.setLongitude(longitude = it.longitude.toString())
                    }
                }
            } else {
                Log.e("Location", "Permission denied or location is disabled")
            }

            //Check location is enabled or not
            if (isLocationEnabled) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        sendTechnicalLocation(prefs.getUserCNIC(), prefs.getLatitude(),prefs.getLongitude(), 1)
                    } catch (e: Exception) {
                        Log.e("LocationWorker", "API call failed: ${e.message}")
                    }
                }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        sendTechnicalLocation(prefs.getUserCNIC(), prefs.getLatitude(),prefs.getLongitude(), 0)
                    } catch (e: Exception) {
                        Log.e("LocationWorker", "API call failed: ${e.message}")
                    }
                }
            }


        }

        Log.d("FCM Message for auto task", "Received message: ${remoteMessage.data}")
    }


    //Function to show Notification

    @SuppressLint("ServiceCast")
    fun showNotification(title: String, message: String) {

        val channelId = "my_channel_id"
        val channelName = "My Notification Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager


        val channel = NotificationChannel(channelId, channelName, importance).apply {
            setShowBadge(true)
        }

        notificationManager.createNotificationChannel(channel)

        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.icon)

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.icon)
            .setLargeIcon(largeIcon)// Make sure you have this icon in drawable
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent) //this will open app on notification click
            .setAutoCancel(true)

        val notificationId = System.currentTimeMillis()
            .toInt() //to avoid notification overlapping. any coming notification will be new.

        notificationManager.notify(notificationId, notificationBuilder.build())
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)

        saveTokenLocally(
            token,
            prefs
        ) //Any new refreshed token will be saved here

    }
}
