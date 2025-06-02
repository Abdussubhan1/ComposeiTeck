package com.example.itecktestingcompose.apiFunctions

import android.util.Log

import com.example.itecktestingcompose.interfaces.RetrofitInterface
import com.example.itecktestingcompose.objects.ServiceBuilder
import com.example.itecktestingcompose.objects.vehicle_details

suspend fun getVehicleDetails(appid:String,T_id:String): Boolean {
    return try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .getVehicleDetails(appid,T_id)

        if (response.isSuccessful) {
            response.body()?.let { responseBody ->
                if (responseBody.Success) {
                    vehicle_details.dataList = responseBody.Data
                    return true
                }
            }
        }
        false
    } catch (e: Exception) {
        Log.d("cnicV", "Exception: ${e.localizedMessage ?: e}")
        false
    }
}

