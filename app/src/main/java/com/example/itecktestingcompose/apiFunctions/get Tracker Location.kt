package com.example.itecktestingcompose.apiFunctions

import com.example.itecktestingcompose.interfaces.RetrofitInterface
import com.example.itecktestingcompose.objects.ServiceBuilder
import com.example.itecktestingcompose.objects.deviceInstallationPlaces

suspend fun getTrackerInstallLocation() {
    try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
            .getTrackerInstallLocation()

        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!
            deviceInstallationPlaces.places = responseBody
        }
    } catch (_: Exception) {
    }
}
