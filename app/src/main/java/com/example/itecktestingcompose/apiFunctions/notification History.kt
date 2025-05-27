package com.example.itecktestingcompose.apiFunctions

import android.util.Log
import com.example.itecktestingcompose.interfaces.RetrofitInterface
import com.example.itecktestingcompose.modelClasses.NotificationHistoryItem
import com.example.itecktestingcompose.objects.ServiceBuilder


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
