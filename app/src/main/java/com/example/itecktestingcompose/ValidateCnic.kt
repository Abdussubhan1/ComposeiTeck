package com.example.itecktestingcompose

import android.util.Log
import com.example.itecktestingcompose.Constants.Constants
import com.example.itecktestingcompose.Interface.RetrofitInterface
import com.example.itecktestingcompose.Interface.ServiceBuilder


data class CNICValidationResult(
    val ifUserExist: Boolean,
    var isLoading: Boolean
)


suspend fun validateCnic(cnic: String): CNICValidationResult {

    var ifUserExist = false

    return try {

        kotlinx.coroutines.delay(1000)

        val response = ServiceBuilder.buildService(RetrofitInterface::class.java).validateCnic(cnic)
        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!
            ifUserExist = responseBody.Success
            if (ifUserExist) {
                Constants.name = responseBody.Name
            }
        }

        CNICValidationResult(ifUserExist, false)
    } catch (e: Exception) {
        Log.d("cnicV", "validateCnic: $cnic")
        CNICValidationResult(false, false)
    }

}
