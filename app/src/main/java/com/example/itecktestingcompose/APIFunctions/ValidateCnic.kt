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

    var ifUserExist: Boolean
    var name = ""

    return try {


        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .validateCnic(cnic, mobileID, FCMToken, appVersion, OSVersion, Brand)

        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!
            ifUserExist = responseBody.Success
            if (ifUserExist) {
                Constants.TechnicianName = responseBody.Name
                Constants.appLoginID = responseBody.AppLoginid
                Constants.technicianID=responseBody.T_ID
                Constants.otp = responseBody.otp
            }
            return CNICValidationResult(ifUserExist, false, name)
        } else {
            CNICValidationResult(ifUserExist = false, isLoading = false, technicianName = "")
        }


    } catch (e: Exception) {
        Log.d("cnicV", "Exception: $e")
        CNICValidationResult(ifUserExist = false, isLoading = false, name)
    }

}
