package com.example.itecktestingcompose.Mainactivity

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.itecktestingcompose.Constants.Constants
import com.example.itecktestingcompose.ModelClasses.LocationWorker
import com.example.itecktestingcompose.functions.AppNavigation
import com.example.itecktestingcompose.functions.getDeviceInfo
import com.example.itecktestingcompose.functions.getSavedToken
import com.example.itecktestingcompose.functions.saveTokenLocally
import com.example.itecktestingcompose.R
import com.example.itecktestingcompose.functions.getAppVersion
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


val jameelNooriFont = FontFamily(Font(R.font.jameelnoori))

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val sharePref = this.getSharedPreferences("UserCNIC", Context.MODE_PRIVATE)
        val storedCNIC = sharePref.getString("CNIC", null)
        Log.d("TAG", "LoginScreen: $storedCNIC")

        enableEdgeToEdge() //FullScreen View of Application
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        } else {
            requestBackgroundLocationPermission()
        }


        val context = this

        val version = getAppVersion(this) //Function to get App Version

        getDeviceInfo(this) //Function to get Mobile related Information

        Constants.FCMToken = getSavedToken(this) ?: "" //Saving the updated token


        setContent {
            AppNavigation(version, context)
        }

    }

    // To prompt user for Notification Permission
    override fun onResume() {
        super.onResume()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }
    }


    private fun requestBackgroundLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                102
            )
        } else {
            startLocationWorker()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            101 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestBackgroundLocationPermission()
                } else {
                    Toast.makeText(this, "Location permission required", Toast.LENGTH_LONG).show()
                    finish()
                }
            }

            102 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationWorker()
                } else {
                    Toast.makeText(
                        this,
                        "Background location permission required",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
            }
        }
    }

    private fun startLocationWorker() {
        Log.d("LocationWorker", "Enqueuing Periodic Work")

        val locationWorkRequest =
            PeriodicWorkRequestBuilder<LocationWorker>(15, TimeUnit.MINUTES).setConstraints(
                Constraints.Builder().setRequiresBatteryNotLow(true)
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "LocationWork",
            ExistingPeriodicWorkPolicy.KEEP,
            locationWorkRequest
        )
    }
}



