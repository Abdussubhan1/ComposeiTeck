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

var gps_time=""
var gsm_Signals=0
var ignition_status=""
var location_status=""
var p_voltages=""


    return try {



        val response =
            ServiceBuilder.buildService(RetrofitInterface::class.java).getStatus(devID)
        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!
            gps_time=responseBody.GPSTime
            gsm_Signals=responseBody.GSMSignal
            ignition_status=responseBody.Ignition
            location_status=responseBody.Location
            p_voltages= responseBody.PowerVoltages
        }

        StatusResult(false,gps_time,gsm_Signals,ignition_status,location_status,p_voltages)


    } catch (e: Exception) {
        Log.d(TAG, "validateDevice: $e")
        StatusResult(false,gps_time,gsm_Signals,ignition_status,location_status,p_voltages)
    }
}