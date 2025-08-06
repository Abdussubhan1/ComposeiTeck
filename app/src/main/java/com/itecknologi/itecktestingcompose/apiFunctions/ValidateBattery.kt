package com.itecknologi.itecktestingcompose.apiFunctions


import android.content.ContentValues.TAG
import android.util.Log
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder

data class BatteryResponse(
    val isLoading: Boolean = false,
    val battery: String =""
)

suspend fun validateBattery(devID: String, statusCheck: Int, eventLog: String): BatteryResponse {
    return try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .validateBattery(devID, statusCheck, eventLog)

        val body = response.body()
        return if (response.isSuccessful && body != null) {
            BatteryResponse(
                isLoading = false,
                battery = body.Battery ?: "Battery status is null"
            )
        } else {
            BatteryResponse(isLoading = false, battery = "Failed to retrieve battery status")
        }
    } catch (e: Exception) {
        Log.e(TAG, "validateBattery error: ${e.message}")
        BatteryResponse(isLoading = false, battery = "No Internet Connection: ${e.message}")
    }
}





