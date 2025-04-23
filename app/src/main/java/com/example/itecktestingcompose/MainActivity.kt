package com.example.itecktestingcompose

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.itecktestingcompose.Constants.Constants
import com.google.firebase.messaging.FirebaseMessaging


val jameelNooriFont = FontFamily(Font(R.font.jameelnoori))

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        onNewToken()
        getDeviceInfo(this)
        generateFCM()

        setContent {
            AppNavigation()
        }
    }

    private fun generateFCM() {

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->

            if (task.isSuccessful) {
                val fcmToken = task.result
                Constants.FCMToken=fcmToken
                Log.d(TAG, "generateFCM: $fcmToken")
            } else {
                Log.e(TAG, "Error getting FCM token: " + task.exception);
            }
        }


    }
}



