package com.example.itecktestingcompose.Constants

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import java.util.ArrayList

object Constants {

    var name:String=""
    var baseURL:String="https://api.itecknologi.com/automated_testing/"
    var deviceID:String=""
    var initialPictures=mutableStateListOf<Bitmap?>(null, null)
    var deviceLat:Double=0.0
    var deviceLong:Double=0.0
}