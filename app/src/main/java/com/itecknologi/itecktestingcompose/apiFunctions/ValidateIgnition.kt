package com.itecknologi.itecktestingcompose.apiFunctions

import android.content.ContentValues.TAG
import android.util.Log
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder

data class IgnitionResponse(
    var isLoading: Boolean = false,
    var ignition: String = "Status Not Found"
)

suspend fun validateIgnition(devID: String): IgnitionResponse {
    return try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java).validateIgnition(devID)

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null && body.Ignition.isNotEmpty()) {
                return IgnitionResponse(
                    isLoading = false,
                    ignition = body.Ignition
                )
            }
        }

        Log.w(TAG, "validateIgnition: Response failed or ignition is null")
        IgnitionResponse()

    } catch (e: Exception) {
        Log.e(TAG, "validateIgnition error: ${e.message}")
        IgnitionResponse()
    }
}
