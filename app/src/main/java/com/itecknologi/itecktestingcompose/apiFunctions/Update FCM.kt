package com.itecknologi.itecktestingcompose.apiFunctions

import android.content.ContentValues.TAG
import android.util.Log
import com.itecknologi.itecktestingcompose.constants.Constants
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder

suspend fun FCMUpdate(appID: String,fcm: String): Boolean{

    return try {

        val response =
            ServiceBuilder.buildService(RetrofitInterface::class.java).FCMUpdated(appID,fcm)
        if (response.isSuccessful && response.body() != null) {

            val responseBody = response.body()!!
            if (responseBody.Success) {
                Constants.authKey=responseBody.Authkey
                Log.d("check authkey", "validateDevice: ${responseBody.Authkey}")
                return true
            }
        }
        false

    } catch (e: Exception) {
        Log.d(TAG, "validateDevice: $e")
        false
    }
}