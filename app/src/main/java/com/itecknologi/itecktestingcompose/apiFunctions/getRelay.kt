package com.itecknologi.itecktestingcompose.apiFunctions

import android.content.ContentValues.TAG
import android.util.Log
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder

data class RelayResponse(
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val message: String = "Error Sending Command!"
)

suspend fun setRelayStatus(devID: String, cmd: String): RelayResponse {
    return try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .getRelay(devID, cmd)

        val body = response.body()
        if (body != null) {
            RelayResponse(
                success = body.Success,
                isLoading = false,
                message = body.Message.ifEmpty { "No Message from Server" }
            )
        } else {
            RelayResponse()
        }

    } catch (e: Exception) {
        Log.e(TAG, "setRelayStatus error: ${e.message}")
        RelayResponse()
    }
}

