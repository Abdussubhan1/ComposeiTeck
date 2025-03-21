package com.example.itecktestingcompose


import android.content.ContentValues.TAG
import android.util.Log
import com.example.itecktestingcompose.Interface.RetrofitInterface
import com.example.itecktestingcompose.Interface.ServiceBuilder


data class LocValidationResult(
    var latitude: Double,
    var longitude: Double
)


suspend fun validateLoc(devID: String): LocValidationResult {

    var lat = 0.0
    var long=0.0

    return try {

        val response =
            ServiceBuilder.buildService(RetrofitInterface::class.java).validateLocation(devID)
        if (response.isSuccessful && response.body() != null) {
            var responseBody = response.body()!!
            lat= responseBody.Lat
            long= responseBody.Lng
        }

        LocValidationResult(lat,long)


    } catch (e: Exception) {
        Log.d(TAG, "validateDevice: $e")
        LocValidationResult(lat,long)
    }
}



