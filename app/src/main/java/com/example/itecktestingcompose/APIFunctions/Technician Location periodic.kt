package com.example.itecktestingcompose.APIFunctions

import android.util.Log
import com.example.itecktestingcompose.Constants.Constants
import com.example.itecktestingcompose.Interface.RetrofitInterface
import com.example.itecktestingcompose.Interface.ServiceBuilder




suspend fun sendTechnicalLocation(
    cnic: String,
    latitude: String,
    longitude: String

): String {

    return try {


        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .getTechnicianLocation(cnic, latitude.toDouble(), longitude.toDouble())

        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!

            return  responseBody.Message
        }

        return "Failed To Get Location"
    } catch (e: Exception) {
        Log.d("cnicV", "Exception: $e")
        return "Failed To Get Location"
    }

}