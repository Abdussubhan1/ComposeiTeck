package com.example.itecktestingcompose.APIFunctions

import android.content.ContentValues.TAG
import android.util.Log
import com.example.itecktestingcompose.Interface.RetrofitInterface
import com.example.itecktestingcompose.Interface.ServiceBuilder
import com.example.itecktestingcompose.ModelClasses.NotificationHistoryItem


suspend fun notificationHistory(cnic: String): List<NotificationHistoryItem> {
    return try {
        val service = ServiceBuilder.buildService(RetrofitInterface::class.java)
        val response = service.getNotificationHistory(cnic)

        if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            emptyList()
        }
    } catch (e: Exception) {
        Log.e("NotificationHistory", "Error: ${e.message}")
        emptyList()
    }
}
