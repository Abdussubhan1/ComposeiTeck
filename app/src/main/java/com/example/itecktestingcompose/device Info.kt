package com.example.itecktestingcompose
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import com.example.itecktestingcompose.Constants.Constants

@SuppressLint("HardwareIds")
fun getDeviceInfo(context: Context) {
    val deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    val osVersion = Build.VERSION.RELEASE
    val brand = Build.BRAND
    val model = Build.MODEL

    Constants.mobileID = deviceId
    Constants.osVersion = osVersion
    Constants.brand = brand
    Constants.model = model


}