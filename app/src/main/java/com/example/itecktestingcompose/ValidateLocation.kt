package com.example.itecktestingcompose


import android.content.ContentValues.TAG
import android.util.Log
import com.example.itecktestingcompose.Constants.Constants
import com.example.itecktestingcompose.Interface.RetrofitInterface
import com.example.itecktestingcompose.Interface.ServiceBuilder


suspend fun validateLoc(devID: String) {

    try {

        val response =
            ServiceBuilder.buildService(RetrofitInterface::class.java).validateLocation(devID)
        if (response.isSuccessful && response.body() != null) {
            var responseBody = response.body()!!
            if (responseBody.Success) {

                Constants.deviceLat = responseBody.Lat

                Constants.deviceLong = responseBody.Lng
            }
        }

    } catch (e: Exception) {
        Log.d(TAG, "validateDevice: $e")
    }
}



