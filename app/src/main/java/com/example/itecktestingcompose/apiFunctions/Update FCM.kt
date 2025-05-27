package com.example.itecktestingcompose.apiFunctions

import android.content.ContentValues.TAG
import android.util.Log
import com.example.itecktestingcompose.interfaces.RetrofitInterface
import com.example.itecktestingcompose.objects.ServiceBuilder

suspend fun FCMUpdate(appID: String,fcm: String): Boolean{

    return try {

        val response =
            ServiceBuilder.buildService(RetrofitInterface::class.java).FCMUpdated(appID,fcm)
        if (response.isSuccessful && response.body() != null) {

            var responseBody = response.body()!!
            if (responseBody.Success) {
                return true
            }
        }
        false

    } catch (e: Exception) {
        Log.d(TAG, "validateDevice: $e")
        false
    }
}