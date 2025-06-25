package com.itecknologi.itecktestingcompose.apiFunctions

import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder


data class CheckLoginResponse(
    val success: Boolean,
    val message: String
)

suspend fun checkLogin(
    appID: String,
    appVersion: String
): CheckLoginResponse {
    return try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .checkLogin(appID, appVersion)

        val body = response.body()
        if (response.isSuccessful && body != null) {
            CheckLoginResponse(
                success = body.success,
                message = body.message
            )
        } else {
            CheckLoginResponse(
                success = false,
                message = "Response error: ${response.code()} ${response.message() ?: ""}"
            )
        }
    } catch (e: Exception) {
        CheckLoginResponse(
            success = false,
            message = "Error: ${e.localizedMessage ?: "Unknown error"}"
        )
    }
}


