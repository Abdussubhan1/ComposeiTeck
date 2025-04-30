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
import androidx.core.content.edit


class MyFirebaseMessagingService : FirebaseMessagingService() {
    @SuppressLint("ServiceCast")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {


        if (remoteMessage.data.isNotEmpty() && remoteMessage.data["title"] != "Location Update") {

            val title = remoteMessage.data["title"] ?: "Default Title"
            val body = remoteMessage.data["body"] ?: "Default Body"
            val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            sharedPref.edit { putBoolean("hasNewNotification", true) }

            showNotification(title, body)

        }

        if (remoteMessage.data.isNotEmpty() && remoteMessage.data["title"] == "Location Update") {

            val fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(applicationContext)

            val sharePref1 =
                applicationContext.getSharedPreferences("UserCNIC", Context.MODE_PRIVATE)
            val sharePref =
                applicationContext.getSharedPreferences("Location", Context.MODE_PRIVATE)

//            if (ContextCompat.checkSelfPermission(
//                    applicationContext,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
//                    location?.let {
//                        sharePref.edit().apply {
//                            putString("Lat", it.latitude.toString())
//                            putString("Lng", it.longitude.toString())
//                            apply()
//                        }
//                    }
//                }
//
//            }

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
                        sharePref.edit().apply {
                            putString("Lat", it.latitude.toString())
                            putString("Lng", it.longitude.toString())
                            apply()
                        }
                    }
                }
            } else {
                Log.e("Location", "Permission denied or location is disabled")
            }

            val storedCNIC = sharePref1.getString("CNIC", null)
            val lat = sharePref.getString("Lat", null)
            val lng = sharePref.getString("Lng", null)

            if (storedCNIC != null && lat != null && lng != null) {
                //Check location is enabled or not
                if(isLocationEnabled) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            sendTechnicalLocation(storedCNIC, lat, lng, 1)
                        } catch (e: Exception) {
                            Log.e("LocationWorker", "API call failed: ${e.message}")
                        }
                    }
                }
                else{
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            sendTechnicalLocation(storedCNIC, lat, lng, 0)
                        } catch (e: Exception) {
                            Log.e("LocationWorker", "API call failed: ${e.message}")
                        }
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
        Log.d("FCM_TOKEN", "New token: $token")

        saveTokenLocally(
            applicationContext,
            token
        ) //Any new refreshed token will be saved here

    }
}
