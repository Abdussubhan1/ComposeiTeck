package com.itecknologi.itecktestingcompose.apiFunctions


import android.content.ContentValues.TAG
import android.util.Log
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder

data class ValidateLocationResponse(
    var isLoading: Boolean,
    val Lat: Double,
    val Lng: Double,
    val Message: String = "",
    val Success: Boolean
)

suspend fun validateLoc(devID: String): ValidateLocationResponse {
    return try {
        val service = ServiceBuilder.buildService(RetrofitInterface::class.java)
        val response = service.validateLocation(devID)

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null && body.Success) {
                Log.d(TAG, "validateDevice: ${body.Lat}, ${body.Lng}")
                return ValidateLocationResponse(false,body.Lat, body.Lng, body.Message, body.Success)
            }
        }

        ValidateLocationResponse(false,0.0,0.0,"Failed", false) // Fallback default if not successful

    } catch (e: Exception) {
        Log.e(TAG, "validateDevice error: ${e.message}")
        ValidateLocationResponse(false,0.0,0.0,"Failed", false)
    }
}




