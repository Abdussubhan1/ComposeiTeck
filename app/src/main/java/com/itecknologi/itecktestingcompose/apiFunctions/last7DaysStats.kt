package com.itecknologi.itecktestingcompose.apiFunctions

import android.content.ContentValues.TAG
import android.util.Log
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder

data class statisticsResponse(
    val installation: Int,
    val redo: Int,
    val removal: Int,
    val total: Int
)
suspend fun getStatsOfLast7Days(cnic: String, duration: Int): statisticsResponse {
    return try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .getStatsOfLast7Days(cnic, duration)
        val body = response.body()

        if (body != null) {
            val total: Int = (body.Installation ?: 0) +
                    (body.Redo ?: 0) +
                    (body.Removal ?: 0)

            statisticsResponse(
                installation = body.Installation ?: 0,
                redo = body.Redo ?: 0,
                removal = body.Removal ?: 0,
                total = total
            )
        } else {
            Log.e("API_ERROR", "Error: ${response.code()} - ${response.message()}")
            statisticsResponse(0,0,0,0)
        }
    } catch (e: Exception) {
        Log.d(TAG, "getStatus: $e")
        statisticsResponse(0,0,0,0)
    }
}
