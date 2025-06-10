package com.itecknologi.itecktestingcompose.apiFunctions

import android.content.ContentValues.TAG
import android.util.Log
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder


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



