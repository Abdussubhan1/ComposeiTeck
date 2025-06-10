package com.itecknologi.itecktestingcompose.apiFunctions

import android.util.Log
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder


suspend fun sendTechnicalLocation(
    cnic: String,
    latitude: String,
    longitude: String,
    gpsstatus: Int

): String {

     try {


        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .getTechnicianLocation(cnic, latitude.toDouble(), longitude.toDouble(),gpsstatus)

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