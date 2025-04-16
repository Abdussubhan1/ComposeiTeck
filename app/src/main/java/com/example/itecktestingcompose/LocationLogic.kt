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
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.coroutineScope


@Composable
fun getLocation(): Boolean {
    val context = LocalContext.current
    var flag by remember { mutableStateOf(false) }
    var mobLat by remember { mutableStateOf<Double?>(null) }
    var mobLong by remember { mutableStateOf<Double?>(null) }

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

    // Observe permission changes

    if (!hasPermission) {
        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    } else {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                mobLat = it.latitude
                mobLong = it.longitude

                Log.d(TAG, "getLocation: ${it.latitude} ${it.longitude}")

                if (mobLat != null && mobLong != null) {
                    flag = checkLocationWithinRange(
                        dLat = Constants.deviceLat,
                        dLong = Constants.deviceLong,
                        mobLat = mobLat!!,
                        mobLong = mobLong!!
                    )



//                        validateLoc(Constants.deviceID)

                }
            }
        }
    }


//    // Use this to validate when location is available
//    LaunchedEffect(mobLat, mobLong) {
//
//    }

    return flag
}


fun checkLocationWithinRange(
    dLat: Double,
    dLong: Double,
    mobLat: Double?,
    mobLong: Double?
): Boolean {
    if (mobLat == null || mobLong == null) return false

    val results = FloatArray(1)

    // Calculate distance between the device's coordinates and mobile's coordinates
    Location.distanceBetween(dLat, dLong, mobLat, mobLong, results)

    // results[0] contains the distance in meters
    val distanceInMeters = results[0]

    // Return true if within 25 meters, else false
    return distanceInMeters <= 500000

}