package com.example.itecktestingcompose

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.itecktestingcompose.Constants.Constants
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import android.Manifest
import android.content.ContentValues.TAG
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import com.example.itecktestingcompose.Interface.RetrofitInterface
import com.example.itecktestingcompose.Interface.ServiceBuilder
import kotlinx.coroutines.coroutineScope
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

var deviceLocation: Pair<Double, Double>? = null

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun getLocation(): Boolean {
    var deviceLocationResult by remember {
        mutableStateOf(ValidateLocationResponse(0.0000, 0.0000))
    }
    var mobLat: Double?
    var mobLong: Double?
    var flag by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
    }




    coroutineScope.launch {

//        validateLoc(Constants.deviceID)
        test()
        if (!hasPermission) {
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (hasPermission) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    mobLat = it.latitude
                    mobLong = it.longitude
//                    Log.d(TAG, "getLocation: $mobLat $mobLong")
                    val isWithinRange = checkLocationWithinRange(
                        dLat = deviceLocation?.first!!,
                        dLong = deviceLocation?.second!!, mobLat, mobLong
                    )

                    flag = isWithinRange
                }
            }
        }

    }


    return flag
}

suspend fun test(): ValidateLocationResponse {
    return try {
        var lat = 0.0
        var long = 0.0

        val response =
            ServiceBuilder.buildService(RetrofitInterface::class.java)
                .validateLocation(Constants.deviceID)
        if (response.isSuccessful && response.body() != null) {
            var responseBody = response.body()!!
            if (responseBody.Success) {
                lat = responseBody.Lat
                long = responseBody.Lng
                deviceLocation = Pair(lat, long)
                Log.d(TAG, "validateDevice: $lat $long")

            }

        }

        (ValidateLocationResponse(lat, long))

    } catch (e: Exception) {
        Log.d(TAG, "validateDevice: $e")
        (ValidateLocationResponse(0.0, 0.0))
    }
}

fun checkLocationWithinRange(
    dLat: Double,
    dLong: Double,
    mobLat: Double?,
    mobLong: Double?
): Boolean {
    Log.d(TAG, "checkLocationWithinRange: device($dLat, $dLong), mobile($mobLat, $mobLong)")

    if (mobLat == null || mobLong == null) return false

    val earthRadius = 6371000.0 // Radius of Earth in meters

    val latDistance = Math.toRadians(mobLat - dLat)
    val lonDistance = Math.toRadians(mobLong - dLong)
    Log.d(TAG, "checkLocationWithinRange: $latDistance $lonDistance")

    val a = sin(latDistance / 2).pow(2.0) +
            cos(Math.toRadians(dLat)) * cos(Math.toRadians(mobLat)) *
            sin(lonDistance / 2).pow(2.0)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    val distanceInMeters = earthRadius * c
    Log.d(TAG, "checkLocationWithinRange: $distanceInMeters")

    // Return true if within 500 meters, else false
    return distanceInMeters <= 50000

}