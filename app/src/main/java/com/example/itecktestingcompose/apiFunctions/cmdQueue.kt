package com.example.itecktestingcompose.apiFunctions

import com.example.itecktestingcompose.interfaces.RetrofitInterface
import com.example.itecktestingcompose.objects.ServiceBuilder


suspend fun cmdQueueCheck(
    devID: String,
    cmd: String
): String {
    return try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .getCmdQueueStatus(devID, cmd)

        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!
            if (responseBody.Success) {
                responseBody.Message
            } else {
                "Command already in queue"
            }
        } else {
            "Error!"
        }
    } catch (e: Exception) {
        "Error!"
    }
}

