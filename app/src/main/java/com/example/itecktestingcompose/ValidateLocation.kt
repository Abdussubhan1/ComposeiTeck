package com.example.itecktestingcompose


import android.content.ContentValues.TAG
import android.util.Log
import com.example.itecktestingcompose.Constants.Constants
import com.example.itecktestingcompose.Interface.RetrofitInterface
import com.example.itecktestingcompose.Interface.ServiceBuilder

data class ValidateLocationResponse(
    val Success: Boolean,
    var isLoading: Boolean,
    val Lat: Double,
    val Lng: Double
)

suspend fun validateLoc(devID: String): ValidateLocationResponse {
    return try {
        val service = ServiceBuilder.buildService(RetrofitInterface::class.java)
        val response = service.validateLocation(devID)

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null && body.Success) {
                Log.d(TAG, "validateDevice: ${body.Lat}, ${body.Lng}")
                return ValidateLocationResponse(body.Success,false,body.Lat, body.Lng)
            }
        }

        ValidateLocationResponse(false,false,0.0, 0.0) // Fallback default if not successful

    } catch (e: Exception) {
        Log.e(TAG, "validateDevice error: ${e.message}")
        ValidateLocationResponse(false,false,0.0, 0.0)
    }
}




