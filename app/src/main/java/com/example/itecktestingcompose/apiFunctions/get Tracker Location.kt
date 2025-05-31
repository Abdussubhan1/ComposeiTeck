package com.example.itecktestingcompose.apiFunctions

import android.util.Log
import com.example.itecktestingcompose.interfaces.RetrofitInterface
import com.example.itecktestingcompose.modelClasses.TrackerLocation
import com.example.itecktestingcompose.objects.ServiceBuilder
import com.example.itecktestingcompose.objects.deviceInstallationPlaces

suspend fun getTrackerInstallLocation() {
    try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .getTrackerInstallLocation()

        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!
            deviceInstallationPlaces.places = responseBody
            Log.e("Tracker Places", "getTrackerInstallLocation: $responseBody")
        }
    } catch (e: Exception) {
        Log.e("Tracker Places", "getTrackerInstallLocation: ${e.message}", e)

    }
}
