package com.itecknologi.itecktestingcompose.apiFunctions

import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder

data class relayResponse(
    var success: Boolean,
    var isLoading: Boolean,
    var message: String
)

suspend fun setRelayStatus(
    devID: String,
    cmd: String
): relayResponse {
    return try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .getRelay(devID, cmd)

        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!

            relayResponse(
                success = responseBody.Success,
                isLoading = false,
                message = responseBody.Message
            )
        } else {
            relayResponse(
                success = false,
                isLoading = false,
                message = "Error Sending Command!"
            )
        }

    } catch (e: Exception) {
        relayResponse(
            success = false,
            isLoading = false,
            message = "Error Sending Command!"
        )
    }
}

