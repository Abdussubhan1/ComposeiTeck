package com.itecknologi.itecktestingcompose.apiFunctions

import android.content.ContentValues.TAG
import android.util.Log
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder

data class ignitionResponse(
    var isLoading: Boolean,
    var ignition: String?
)



suspend fun validateIgnition(devID: String) : ignitionResponse {

    var ignition : String? = null

    return try {

        val response =
            ServiceBuilder.buildService(RetrofitInterface::class.java).validateIgnition(devID)
        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!
            ignition = responseBody.Ignition

        }
        ignitionResponse(isLoading = false, ignition = ignition)

    } catch (e: Exception) {
        Log.d(TAG, "validateDevice: $e")
        ignitionResponse(isLoading = false, ignition = ignition)
    }
}