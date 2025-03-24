package com.example.itecktestingcompose



import android.content.ContentValues.TAG
import android.util.Log
import com.example.itecktestingcompose.Interface.RetrofitInterface
import com.example.itecktestingcompose.Interface.ServiceBuilder


suspend fun validateBattery(devID: String) :String{

    var battery=""

try {

        val response =
            ServiceBuilder.buildService(RetrofitInterface::class.java).validateBattery(devID)
        if (response.isSuccessful && response.body() != null) {
            var responseBody = response.body()!!
            battery = responseBody.Battery

        }
        return battery

    } catch (e: Exception) {
        Log.d(TAG, "validateDevice: $e")
       return "Error"
    }
}



