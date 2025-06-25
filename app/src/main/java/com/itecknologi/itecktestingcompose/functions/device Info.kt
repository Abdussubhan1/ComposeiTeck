package com.itecknologi.itecktestingcompose.functions
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.itecknologi.itecktestingcompose.constants.Constants

@SuppressLint("HardwareIds")
fun getDeviceInfo(context: Context) {
    val deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    val osVersion = Build.VERSION.RELEASE
    val brand = Build.BRAND

    Constants.mobileID = deviceId
    Constants.osVersion = osVersion
    Constants.brand = brand

}