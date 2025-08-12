package com.itecknologi.itecktestingcompose.apiFunctions

import android.content.ContentValues.TAG
import android.util.Log
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder


suspend fun getStatsOfLast7Days(cnic: String): Map<String, Int>? {

    return try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .getStatsOfLast7Days(cnic)

        if (response.isSuccessful) {
            response.body() // Map<String, Int> directly
        } else {
            Log.e("API_ERROR", "Error: ${response.code()} - ${response.message()}")
            null
        }


    } catch (e: Exception) {
        Log.d(TAG, "getStatus: $e")
        null
    }
}