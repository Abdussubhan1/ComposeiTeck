package com.example.itecktestingcompose.Mainactivity

import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.itecktestingcompose.Constants.Constants
import com.example.itecktestingcompose.functions.AppNavigation
import com.example.itecktestingcompose.functions.getDeviceInfo
import com.example.itecktestingcompose.functions.getSavedToken
import com.example.itecktestingcompose.functions.saveTokenLocally
import com.example.itecktestingcompose.R
import com.example.itecktestingcompose.functions.getAppVersion
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch


val jameelNooriFont = FontFamily(Font(R.font.jameelnoori))

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge() //FullScreen View of Application
        super.onCreate(savedInstanceState)

        val context = this
        val version = getAppVersion(this) //Function to get App Version
        generateFCM() //Function to get FCM Token
        getDeviceInfo(this) //Function to get Mobile related Information

        Constants.FCMToken = getSavedToken(this) ?: "" //Saving the updated token

        //User will be asked to give permission to send notifications

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    1001 // requestCode
                )
            }
        }

        setContent {
            AppNavigation(version,context)
        }

//        lifecycleScope.launch {
//            val notificationHistory = com.example.itecktestingcompose.APIFunctions.notificationHistory(Constants.cnic)
//        }


    }

    private fun generateFCM() {

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val fcmToken = task.result
                Constants.FCMToken= fcmToken
                Log.d(TAG, "FCM Token: $fcmToken")
                saveTokenLocally(this, fcmToken)
            } else {
                Log.e(TAG, "Error getting FCM token: " + task.exception);
            }
        }
    }
}



