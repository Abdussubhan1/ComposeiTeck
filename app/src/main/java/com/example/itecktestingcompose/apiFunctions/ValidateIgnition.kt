package com.example.itecktestingcompose.apiFunctions

import android.content.ContentValues.TAG
import android.util.Log
import com.example.itecktestingcompose.interfaces.RetrofitInterface
import com.example.itecktestingcompose.objects.ServiceBuilder

data class ignitionResponse(
    var isLoading: Boolean,
    var ignition: String
)



suspend fun validateIgnition(devID: String) : ignitionResponse {

    var ignition=""

    return try {

        val response =
            ServiceBuilder.buildService(RetrofitInterface::class.java).validateIgnition(devID)
        if (response.isSuccessful && response.body() != null) {
            var responseBody = response.body()!!
            ignition = responseBody.Ignition

        }
        ignitionResponse(isLoading = false, ignition = ignition)

    } catch (e: Exception) {
        Log.d(TAG, "validateDevice: $e")
        ignitionResponse(isLoading = false, ignition = ignition)
    }
}