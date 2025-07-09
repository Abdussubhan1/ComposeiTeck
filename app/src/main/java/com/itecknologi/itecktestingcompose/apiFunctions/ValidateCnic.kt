package com.itecknologi.itecktestingcompose.apiFunctions

import android.util.Log
import com.itecknologi.itecktestingcompose.constants.Constants
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder


data class CNICValidationResult(
    val ifUserExist: Boolean,
    val isLoading: Boolean,
    val technicianName: String,
    val code: String = ""
)

suspend fun validateCnic(
    cnic: String,
    mobileID: String,
    FCMToken: String,
    appVersion: String,
    OSVersion: String,
    Brand: String
): CNICValidationResult {
    return try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .validateCnic(cnic, mobileID, FCMToken, appVersion, OSVersion, Brand)

        val body = response.body()

        if (response.isSuccessful && body != null) {

            if (body.Success) {
                Constants.TechnicianName = body.Name
                Constants.appLoginID = body.AppLoginid
                Constants.technicianID = body.T_ID
                Constants.authKey = body.Authkey

                CNICValidationResult(
                    ifUserExist =body.Success,
                    isLoading = false,
                    technicianName = body.Name,
                    code = body.otp
                )

            }
            else
                CNICValidationResult(
                ifUserExist = false,
                isLoading = false,
                technicianName = ""
            )


        } else {
            Log.d("cnicV", "Unsuccessful response: ${response.code()} - ${response.message()}")
            CNICValidationResult(
                ifUserExist = false,
                isLoading = false,
                technicianName = ""
            )
        }
    } catch (e: Exception) {
        Log.e("cnicV", "Exception occurred: ${e.localizedMessage}", e)
        CNICValidationResult(
            ifUserExist = false,
            isLoading = false,
            technicianName = ""
        )
    }
}
