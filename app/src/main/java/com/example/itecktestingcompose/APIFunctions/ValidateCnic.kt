package com.example.itecktestingcompose.APIFunctions

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.example.itecktestingcompose.Constants.Constants
import com.example.itecktestingcompose.Interface.RetrofitInterface
import com.example.itecktestingcompose.Interface.ServiceBuilder


data class CNICValidationResult(
    val ifUserExist: Boolean,
    var isLoading: Boolean,
    var technicianName: String
)


suspend fun validateCnic(
    cnic: String,
    mobileID: String,
    FCMToken: String,
    appVersion: String,
    OSVersion: String,
    Brand: String
): CNICValidationResult {

    var ifUserExist = false
    var name = ""

    return try {


        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .validateCnic(cnic, mobileID, FCMToken, appVersion, OSVersion, Brand)

        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!
            ifUserExist = responseBody.Success
            if (ifUserExist) {
                name = responseBody.Name
                Constants.appLoginID = responseBody.AppLoginid
                Constants.otp = responseBody.otp
            }
            CNICValidationResult(ifUserExist, false, name)
        }

        CNICValidationResult(ifUserExist = false, isLoading = false, technicianName = "")
    } catch (e: Exception) {
        Log.d("cnicV", "Exception: $e")
        CNICValidationResult(ifUserExist = false, isLoading = false, name)
    }

}
