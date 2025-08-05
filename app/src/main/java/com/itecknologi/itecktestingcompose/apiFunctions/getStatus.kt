package com.itecknologi.itecktestingcompose.apiFunctions

import android.content.ContentValues.TAG
import android.util.Log
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder

data class StatusResult(
    val isLoading: Boolean,
    val GPSTime: String?,
    val GSMSignal: Int,
    val Ignition: String?,
    val Location: String?,
    val PowerVoltages: String?
)

suspend fun getStatus(devID: String): StatusResult {
    return try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java).getStatus(devID)

        val body = response.body()
        if (body != null) {
            StatusResult(
                isLoading = false,
                GPSTime = body.GPSTime,
                GSMSignal = body.GSMSignal,
                Ignition = body.Ignition,
                Location = body.Location,
                PowerVoltages = body.PowerVoltages
            )
        } else {
            StatusResult(false, "", 0, "", "", "")
        }

    } catch (e: Exception) {
        Log.d(TAG, "getStatus: $e")
        StatusResult(false, "", 0, "", "", "")
    }
}
