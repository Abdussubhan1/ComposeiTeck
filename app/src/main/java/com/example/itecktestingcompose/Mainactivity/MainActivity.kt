package com.example.itecktestingcompose.Mainactivity

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.itecktestingcompose.Constants.Constants
import com.example.itecktestingcompose.functions.AppNavigation
import com.example.itecktestingcompose.functions.getDeviceInfo
import com.example.itecktestingcompose.functions.getSavedToken
import com.example.itecktestingcompose.functions.saveTokenLocally
import com.example.itecktestingcompose.R
import com.google.firebase.messaging.FirebaseMessaging


val jameelNooriFont = FontFamily(Font(R.font.jameelnoori))

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        Constants.FCMToken = getSavedToken(this) ?: "" //Saving the updated token

        super.onCreate(savedInstanceState)

        enableEdgeToEdge() //FullScreen View of Application

        getDeviceInfo(this) //Function to get Mobile related Information

        generateFCM() //Function to get FCM Token

        setContent {
            AppNavigation()
        }
    }

    private fun generateFCM() {

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val fcmToken = task.result
                saveTokenLocally(this, fcmToken)
            } else {
                Log.e(TAG, "Error getting FCM token: " + task.exception);
            }
        }
    }
}



