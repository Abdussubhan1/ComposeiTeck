package com.itecknologi.itecktestingcompose.apiFunctions


import android.content.ContentValues.TAG
import android.util.Log
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder

data class batteryResponse(
    var isLoading: Boolean,
    var battery: String?
)


suspend fun validateBattery(devID: String): batteryResponse {

    var battery: String? = null

   return try {

        val response =
            ServiceBuilder.buildService(RetrofitInterface::class.java).validateBattery(devID)
        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!
            battery = responseBody.Battery

        }
         batteryResponse(isLoading = false, battery = battery)

    } catch (e: Exception) {
        Log.d(TAG, "validateDevice: $e")
       batteryResponse(isLoading = false, battery = battery)
    }
}



