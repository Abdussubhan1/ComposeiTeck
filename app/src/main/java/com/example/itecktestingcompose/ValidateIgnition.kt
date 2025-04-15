package com.example.itecktestingcompose

import android.content.ContentValues.TAG
import android.util.Log
import com.example.itecktestingcompose.Interface.RetrofitInterface
import com.example.itecktestingcompose.Interface.ServiceBuilder

suspend fun validateIgnition(devID: String) :String{

    var ignition=""

    try {

        val response =
            ServiceBuilder.buildService(RetrofitInterface::class.java).validateIgnition(devID)
        if (response.isSuccessful && response.body() != null) {
            var responseBody = response.body()!!
            ignition = responseBody.Ignition

        }
        return ignition

    } catch (e: Exception) {
        Log.d(TAG, "validateDevice: $e")
        return "Error"
    }
}