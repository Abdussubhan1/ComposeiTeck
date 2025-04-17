package com.example.itecktestingcompose


import android.content.ContentValues.TAG
import android.util.Log
import com.example.itecktestingcompose.Constants.Constants
import com.example.itecktestingcompose.Interface.RetrofitInterface
import com.example.itecktestingcompose.Interface.ServiceBuilder

data class ValidateLocationResponse(
    val Lat: Double,
    val Lng: Double
)


suspend fun validateLoc(devID: String):ValidateLocationResponse {
    var lat = 0.0
    var long = 0.0

    return try {

        val response = ServiceBuilder.buildService(RetrofitInterface::class.java).validateLocation(devID)
        if (response.isSuccessful && response.body() != null) {
            var responseBody = response.body()!!
            if (responseBody.Success) {
                 lat = responseBody.Lat
                 long = responseBody.Lng
                Log.d(TAG, "validateDevice: $lat $long")

            }

        }

        (ValidateLocationResponse(lat, long))

    } catch (e: Exception) {
        Log.d(TAG, "validateDevice: $e")
        (ValidateLocationResponse(0.0, 0.0))
    }
}



