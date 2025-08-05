package com.itecknologi.itecktestingcompose.apiFunctions


import android.content.ContentValues.TAG
import android.util.Log
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder

data class ValidateLocationResponse(
    var isLoading: Boolean = false,
    val Lat: Double = 0.0,
    val Lng: Double = 0.0,
    val Message: String = "Location Not Found",
    val Success: Boolean = false
)

suspend fun validateLoc(devID: String): ValidateLocationResponse {
    return try {
        val service = ServiceBuilder.buildService(RetrofitInterface::class.java)
        val response = service.validateLocation(devID)
        val body = response.body()

        if (response.isSuccessful && body != null && body.Success) {
            Log.d(TAG, "validateLoc: ${body.Lat}, ${body.Lng}")
            ValidateLocationResponse(
                isLoading = false,
                Lat = body.Lat,
                Lng = body.Lng,
                Message = body.Message,
                Success = true
            )
        } else if (response.isSuccessful && body != null) {
            Log.w(TAG, "validateLoc failed or body null")
            ValidateLocationResponse(false,0.0,0.0,body.Message,false)
        }
        else ValidateLocationResponse()

    } catch (e: Exception) {
        Log.e(TAG, "validateLoc error: ${e.message}")
        ValidateLocationResponse()
    }
}






