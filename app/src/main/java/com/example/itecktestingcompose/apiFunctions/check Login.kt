package com.example.itecktestingcompose.apiFunctions

import com.example.itecktestingcompose.interfaces.RetrofitInterface
import com.example.itecktestingcompose.objects.ServiceBuilder


data class checkLoginResponse(
    var success: Boolean,
    var message: String,
    var code:Int
)


suspend fun checkLogin(
    appID: String
): checkLoginResponse {
    return try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .checkLogin(appID)

        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!
            checkLoginResponse(success = responseBody.success, message = responseBody.message,code = response.code())
        } else {
            checkLoginResponse(success = false, message = "Response error: ${response.code()}",code = response.code())
        }
    } catch (e: Exception) {
        checkLoginResponse(success = false, message = "Error: ${e.localizedMessage ?: "Unknown error"}", code = 0)
    }
}

