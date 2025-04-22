package com.example.itecktestingcompose.Constants

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import java.util.ArrayList

object Constants {

    var appVersion: String=""
    var model: String=""
    var brand: String=""
    var mobileID: String = ""
    var osVersion: String = ""
    var finalPictures=mutableStateListOf<Bitmap?>(null, null)
    var name:String=""
    var baseURL:String="https://api.itecknologi.com/automated_testing/"
    var deviceID:String=""
    var initialPictures=mutableStateListOf<Bitmap?>(null, null)
    var mobileLocationLat:Double=0.0
    var mobileLocationLong:Double=0.0
    var deviceLocationLat:Double=0.0
    var deviceLocationLong:Double=0.0
    var deviceLocation:String=""


}

