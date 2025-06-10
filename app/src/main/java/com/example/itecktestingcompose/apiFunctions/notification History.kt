package com.itecknologi.itecktestingcompose.apiFunctions

import android.util.Log
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.modelClasses.NotificationHistoryItem
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder


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
