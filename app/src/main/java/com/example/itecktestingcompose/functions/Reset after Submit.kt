package com.example.itecktestingcompose.functions

import androidx.compose.runtime.mutableStateListOf
import com.example.itecktestingcompose.Constants.Constants


fun resetAllData() {
    Constants.apply {
        deviceID = ""
        initialPictures = mutableStateListOf(null, null)
        finalPictures = mutableStateListOf(null, null)
        deviceLocationLat = 0.0
        deviceLocationLong = 0.0
        mobileLocationLat = 0.0
        mobileLocationLong = 0.0
        deviceLocation = ""
        cust_Contact = ""
        vehicleID=""
    }
}
