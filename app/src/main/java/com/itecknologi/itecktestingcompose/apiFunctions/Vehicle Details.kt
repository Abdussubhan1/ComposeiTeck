package com.itecknologi.itecktestingcompose.apiFunctions

import android.util.Log

import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder
import com.itecknologi.itecktestingcompose.objects.vehicle_details

data class getVehicleDetailsResponse(
    val success: Boolean = false,
    val message: String
)

suspend fun getVehicleDetails(T_ID: String, type: String): getVehicleDetailsResponse {
    return try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .getVehicleDetails(T_ID, type)

        response.body()?.let { responseBody ->
            return if (responseBody.Success) {
                vehicle_details.dataList = responseBody.data
                getVehicleDetailsResponse(true, "Details Fetched")
            } else {
                getVehicleDetailsResponse(false, "Details Not Fetched")
            }
        }

        // response.body() was null
        getVehicleDetailsResponse(false, "Vehicle Details Not Updated")
    } catch (e: Exception) {
        Log.d("cnicV", "Exception: ${e.localizedMessage ?: e}")
        getVehicleDetailsResponse(false, "unknown error")
    }
}


