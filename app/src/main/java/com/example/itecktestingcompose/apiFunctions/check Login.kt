package com.itecknologi.itecktestingcompose.apiFunctions

import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder


data class checkLoginResponse(
    var success: Boolean,
    var message: String
)


suspend fun checkLogin(
    appID: String
): checkLoginResponse {
    return try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .checkLogin(appID)

        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!
            checkLoginResponse(success = responseBody.success, message = responseBody.message)
        } else {
            checkLoginResponse(success = false, message = "Response error: ${response.code()}")
        }
    } catch (e: Exception) {
        checkLoginResponse(success = false, message = "Error: ${e.localizedMessage ?: "Unknown error"}")
    }
}

