package com.itecknologi.itecktestingcompose.apiFunctions

import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder

suspend fun jobPendingComments(
    jobAssignedID: String,
    techStatus: String,
    comments: String,
    pocNumberId: Int
): String {
    return try {
        val response =
            ServiceBuilder.buildService(RetrofitInterface::class.java)
                .jobPendingComments(jobAssignedID, techStatus, comments,pocNumberId)
        val body = response.body()
        body?.message ?: "Response error: ${response.code()} ${response.message() ?: ""}"
    } catch (e: Exception) {
        "Error: ${e.localizedMessage ?: "Unknown error"}"
    }
}
