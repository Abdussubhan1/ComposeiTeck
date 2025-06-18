package com.itecknologi.itecktestingcompose.apiFunctions

import android.content.ContentValues.TAG
import android.util.Log
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder


suspend fun cmdQueueCheck(
    devID: String,
    cmd: String
): String {
    return try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .getCmdQueueStatus(devID, cmd)

        val body = response.body()
        if (response.isSuccessful && body != null) {
            if (body.Success) {
                body.Message
            } else {
                "Command already in queue"
            }
        } else {
            "Error!"
        }
    } catch (e: Exception) {
        Log.e(TAG, "cmdQueueCheck error: ${e.message}")
        "Error!"
    }
}


