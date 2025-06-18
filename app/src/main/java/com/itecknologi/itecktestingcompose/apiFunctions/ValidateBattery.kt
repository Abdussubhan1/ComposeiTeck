package com.itecknologi.itecktestingcompose.apiFunctions


import android.content.ContentValues.TAG
import android.util.Log
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder

data class BatteryResponse(
    var isLoading: Boolean = false,
    var battery: String = "Status Not Found"
)

suspend fun validateBattery(devID: String): BatteryResponse {
    return try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java).validateBattery(devID)

        if (response.isSuccessful) {
            val body = response.body()
            if (!body?.Battery.isNullOrEmpty()) {
                return BatteryResponse(
                    isLoading = false,
                    battery = body!!.Battery
                )
            }
        }

        Log.w(TAG, "validateBattery: Response failed or battery is null")
        BatteryResponse()

    } catch (e: Exception) {
        Log.e(TAG, "validateBattery error: ${e.message}")
        BatteryResponse()
    }
}




