package com.example.itecktestingcompose.ModelClasses

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.itecktestingcompose.Constants.Constants
import com.google.android.gms.location.LocationServices
import androidx.core.content.edit
import com.example.itecktestingcompose.APIFunctions.sendTechnicalLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class LocationWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {

        Log.d("LocationWorker", "doWork called")

        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(applicationContext)
        val latch = CountDownLatch(1) // To wait for async completion

        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {


                    val sharePref =
                        applicationContext.getSharedPreferences("Location", Context.MODE_PRIVATE)
                    sharePref.edit().apply {
                        putString("Lat", it.latitude.toString())
                        putString("Lng", it.longitude.toString())
                        apply()
                    }

                    Log.d("LocationWorker", "Lat: ${it.latitude}, Lng: ${it.longitude}")

                    apiCallLambda(applicationContext) { title, message ->
                        showNotification1(applicationContext, title, message)
                    }

                }
                latch.countDown()
            }

            latch.await(10, TimeUnit.SECONDS) // Wait for async completion (up to 10 seconds)

        }
        return Result.success()

    }
}




fun apiCallLambda(context: Context, onSuccess: (String, String) -> Unit) {
    val sharePref1 = context.getSharedPreferences("UserCNIC", Context.MODE_PRIVATE)
    val sharePref = context.getSharedPreferences("Location", Context.MODE_PRIVATE)

    val storedCNIC = sharePref1.getString("CNIC", null)
    val lat = sharePref.getString("Lat", null)
    val lng = sharePref.getString("Lng", null)

    if (storedCNIC != null && lat != null && lng != null) {
        // Launch coroutine globally
        kotlinx.coroutines.GlobalScope.launch {
            try {
                val response = sendTechnicalLocation(storedCNIC, lat, lng)
                onSuccess("Location Update", response)
            } catch (e: Exception) {
                Log.e("LocationWorker", "API call failed: ${e.message}")
            }
        }
    }
}




fun showNotification1(context: Context, title: String, message: String) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channelId = "default_channel"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Default Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, channelId)
        .setContentTitle(title)
        .setContentText(message)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .build()

    notificationManager.notify(1, notification)
}


//@SuppressLint("CoroutineCreationDuringComposition")
//@Composable
//fun apicall() {
//
//    var couroutineScope = rememberCoroutineScope()
//    var context=LocalContext.current
//
//    val sharePref1 =
//        context.getSharedPreferences("UserCNIC", Context.MODE_PRIVATE)
//    val sharePref =
//        context.getSharedPreferences("Location", Context.MODE_PRIVATE)
//
//
//    val storedCNIC = sharePref1.getString("CNIC", null)
//    val lat = sharePref.getString("Lat", null)
//    val lng = sharePref.getString("Lng", null)
//    couroutineScope.launch { var response = sendTechnicalLocation(storedCNIC!!, lat!!, lng!!)
//        showNotification1(context, "Location Update", response)
//    }
//}


