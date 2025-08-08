package com.itecknologi.itecktestingcompose.constants

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf


object Constants {

    var TechnicianName: String = ""
    var cust_Contact: String = ""
    var cnic: String = ""
    var appLoginID: String = ""
    var brand: String = ""
    var mobileID: String = ""
    var osVersion: String = ""
    var finalPictures = mutableStateListOf<Bitmap?>(null, null)
    const val BASE_URL: String = "https://api.itecknologi.com/automated_testing/"
    var deviceID: String = ""
    var initialPictures = mutableStateListOf<Bitmap?>(null, null)
    var mobileLocationLat: Double = 0.0
    var mobileLocationLong: Double = 0.0
    var deviceLocationLat: Double = 0.0
    var deviceLocationLong: Double = 0.0
    var deviceLocation: String = ""
    var vehicleID: String = ""
    var technicianID: Int = 0
    var authKey:String=""
    var installedDeviceType:Int=0
    var immobilizer:Int=0
    var TLocID:Int=0
    var eventLogID:String=""
    var engineNumber:String=""
    var chassisNumber:String=""
    var make:String=""
    var model:String=""
    var JobAssigneddate:String=""
    var VRN:String=""
    var X:Double=0.0
    var Y:Double=0.0
    var technicalJobAssignedID:String=""
    var navigateBackto:Int=0
}


