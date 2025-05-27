package com.example.itecktestingcompose.apiFunctions

import android.content.ContentValues.TAG
import android.util.Log
import com.example.itecktestingcompose.interfaces.RetrofitInterface
import com.example.itecktestingcompose.interfaces.ServiceBuilder


data class DevValidationResult(
    val ifDeviceExist: Boolean,
    var isLoading: Boolean
)



suspend fun validateDev(devID: String): DevValidationResult {

    var ifDeviceExist = false


    return try {

//        kotlinx.coroutines.delay(3000)

        val response =
            ServiceBuilder.buildService(RetrofitInterface::class.java).validateDevice(devID)
        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!
            ifDeviceExist = responseBody.Success
        }

        DevValidationResult(ifDeviceExist, false)


    } catch (e: Exception) {
        Log.d(TAG, "validateDevice: $e")
        DevValidationResult(ifDeviceExist=false,isLoading = false)
    }
}



