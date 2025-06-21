package com.itecknologi.itecktestingcompose.apiFunctions

import android.content.ContentValues.TAG
import android.util.Log
import com.itecknologi.itecktestingcompose.constants.Constants
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder

suspend fun getEventLogID() {

    try {

        val response =
            ServiceBuilder.buildService(RetrofitInterface::class.java).eventLog()
        if (response.isSuccessful && response.body() != null) {

            val responseBody = response.body()!!
            if (responseBody.Success) {
                Constants.eventLogID = responseBody.Event_log

            }
        }

    } catch (e: Exception) {
        Log.d(TAG, "exception error: $e")

    }
}