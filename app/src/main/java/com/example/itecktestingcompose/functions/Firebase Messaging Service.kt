package com.example.itecktestingcompose.functions

import com.google.firebase.messaging.FirebaseMessagingService
import android.util.Log
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle incoming FCM messages here
        Log.d("FCM Message", "Received message: ${remoteMessage.data}")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM_TOKEN", "New token: $token")

        saveTokenLocally(applicationContext, token) //Any new refreshed token will be saved here

//        sendTokenToServer(token)

        //todo CALL API here
    }
}
