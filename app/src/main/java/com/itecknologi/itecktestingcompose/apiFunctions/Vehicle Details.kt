package com.itecknologi.itecktestingcompose.apiFunctions

import android.util.Log

import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.modelClasses.VehData
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder
import com.itecknologi.itecktestingcompose.objects.vehicle_details

data class getVehicleDetailsResponse(
    val success: Boolean = false,
    val message: String
)

suspend fun getVehicleDetails(T_ID:String,type:String): getVehicleDetailsResponse {
    return try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .getVehicleDetails(T_ID,type)

        if (response.isSuccessful) {
            response.body()?.let { responseBody ->
                if (responseBody.success) {
                    vehicle_details.dataList = responseBody.data
                    return getVehicleDetailsResponse(true, responseBody.message)
                }
            }
        }
        getVehicleDetailsResponse(false, "unknown error")
    } catch (e: Exception) {
        Log.d("cnicV", "Exception: ${e.localizedMessage ?: e}")
        getVehicleDetailsResponse(false, "unknown error")
    }
}

