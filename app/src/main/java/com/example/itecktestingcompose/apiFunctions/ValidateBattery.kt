package com.example.itecktestingcompose.apiFunctions


import android.content.ContentValues.TAG
import android.util.Log
import com.example.itecktestingcompose.interfaces.RetrofitInterface
import com.example.itecktestingcompose.interfaces.ServiceBuilder

data class batteryResponse(
    var isLoading: Boolean,
    var battery: String
)


suspend fun validateBattery(devID: String): batteryResponse {

    var battery = ""

   return try {

        val response =
            ServiceBuilder.buildService(RetrofitInterface::class.java).validateBattery(devID)
        if (response.isSuccessful && response.body() != null) {
            var responseBody = response.body()!!
            battery = responseBody.Battery

        }
         batteryResponse(isLoading = false, battery = battery)

    } catch (e: Exception) {
        Log.d(TAG, "validateDevice: $e")
       batteryResponse(isLoading = false, battery = battery)
    }
}



