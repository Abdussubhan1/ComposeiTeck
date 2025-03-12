package com.example.itecktestingcompose

import android.content.ContentValues.TAG
import android.util.Log
import com.example.itecktestingcompose.Interface.RetrofitInterface
import com.example.itecktestingcompose.Interface.ServiceBuilder


suspend fun validateCnic(cnic: String): Boolean {
    var ifUserExist = false
    try {
        val response = ServiceBuilder.buildService(RetrofitInterface::class.java).validateCnic(cnic)
        if (response.isSuccessful && response.body() != null) {
            val responseBody = response.body()!!
            ifUserExist = responseBody.Success
            if (ifUserExist) {
                Constants.name = responseBody.Name
            }
        }
    } catch (e: Exception) {
        Log.d(TAG, "validateCnic: $e")
    }
    return ifUserExist
}