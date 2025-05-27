package com.example.itecktestingcompose.mainActivity

import android.Manifest
import android.content.ContentValues.TAG
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
import com.example.itecktestingcompose.functions.AppNavigation
import com.example.itecktestingcompose.functions.getDeviceInfo
import com.example.itecktestingcompose.R
import com.example.itecktestingcompose.appPrefs.PreferenceManager
import com.google.firebase.messaging.FirebaseMessaging


val jameelNooriFont = FontFamily(Font(R.font.jameelnoori))


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        val prefs = PreferenceManager(this) //Created object for class PreferenceManager
        Log.d("CheckAppLoginID", "AppLoginID: ${prefs.getAppLoginID()}")

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

        generateFCM()

        getDeviceInfo(this) //Function to get Mobile related Information

//        Constants.fcm=getSavedToken(prefs) //Function to get Saved Token
//        Log.d("FCM token", "FCM Token 2: ${Constants.fcm}")


        setContent {
            AppNavigation(this, prefs)
        }
    }

    private fun generateFCM() {
        val prefs = PreferenceManager(this)
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                prefs.setFCM(task.result)
                Log.d("FCM token", "FCM Token 1: ${task.result}")

            } else {
                Log.e(TAG, "Error getting FCM token: ${task.exception}")
            }
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

}



