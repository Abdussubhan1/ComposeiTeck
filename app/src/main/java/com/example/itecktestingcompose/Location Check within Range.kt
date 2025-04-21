package com.example.itecktestingcompose

import android.content.ContentValues.TAG
import android.util.Log
import com.example.itecktestingcompose.Constants.Constants
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

fun checkLocationWithinRange(): Double {
    var mobLat = Constants.mobileLocationLat
    var mobLong = Constants.mobileLocationLong
    var dLat = Constants.deviceLocationLat
    var dLong = Constants.deviceLocationLong
    Log.d(TAG, "checkLocationWithinRange: device($dLat, $dLong), mobile($mobLat, $mobLong)")

    val earthRadius = 6371000.0 // Radius of Earth in meters
    var result: Boolean

    val latDistance = Math.toRadians(mobLat - dLat)
    val lonDistance = Math.toRadians(mobLong - dLong)
    Log.d(TAG, "checkLocationWithinRange: $latDistance $lonDistance")

    val a = sin(latDistance / 2).pow(2.0) +
            cos(Math.toRadians(dLat)) * cos(Math.toRadians(mobLat)) *
            sin(lonDistance / 2).pow(2.0)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    val distanceInMeters = earthRadius * c
    Log.d(TAG, "checkLocationWithinRange: $distanceInMeters")

    return distanceInMeters

    // Return true if within 500 meters, else false
    result = distanceInMeters <= 50

//    return result


}