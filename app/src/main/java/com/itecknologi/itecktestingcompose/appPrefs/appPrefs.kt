package com.itecknologi.itecktestingcompose.appPrefs

import android.content.Context
import android.util.Log
import androidx.core.content.edit

class PreferenceManager(context: Context) {

    private val sharedPref = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    fun setHasNewNotification(value: Boolean) {
        sharedPref.edit { putBoolean("hasNewNotification", value) }
        Log.d("PreferenceManager", "setHasNewNotification called with value: $value")
    }

    fun getHasNewNotification(): Boolean {
        return sharedPref.getBoolean("hasNewNotification", false)
    }

    fun setTechnicianName(name: String) {
        Log.d("PreferenceManager", "setTechnicianName called with name: $name")
        sharedPref.edit { putString("TechnicianName", name) }
    }

    fun getTechnicianName(): String {
        return sharedPref.getString("TechnicianName", "") ?: ""
    }

    fun setUserCNIC(cnic: String) {
        Log.d("PreferenceManager", "setUserCNIC called with cnic: $cnic")
        sharedPref.edit { putString("UserCNIC", cnic) }
    }

    fun getUserCNIC(): String {
        return sharedPref.getString("UserCNIC", "") ?: ""
    }

    fun setLatitude(latitude: String) {
        Log.d("PreferenceManager", "setLatitude called with latitude: $latitude")
        sharedPref.edit { putString("latitude", latitude) }
    }

    fun getLatitude(): String {
        return sharedPref.getString("latitude", "") ?: ""
    }

    fun setLongitude(longitude: String) {
        Log.d("PreferenceManager", "setLongitude called with longitude: $longitude")
        sharedPref.edit { putString("longitude", longitude) }
    }

    fun getLongitude(): String {
        return sharedPref.getString("longitude", "") ?: ""
    }

    fun setAppLoginID(id: String) {
        Log.d("PreferenceManager", "setAppLoginID called with id: $id")
        sharedPref.edit { putString("AppLoginID", id) }
    }

    fun getAppLoginID(): String {
        return sharedPref.getString("AppLoginID", "") ?: ""
    }

    fun setFCM(fcm: String) {
        Log.d("PreferenceManager", "setAppLoginID called with id: $fcm")
        sharedPref.edit { putString("FCM", fcm) }
    }

    fun getFCM(): String {
        return sharedPref.getString("FCM", "") ?: ""
    }

    fun setTechnicianID(T_ID: Int) {
        Log.d("PreferenceManager", "setTechnicianID called with id: $T_ID")
        sharedPref.edit { putInt("TechnicianID", T_ID) }
    }

    fun getTechnicianID(): Int {
        return sharedPref.getInt("TechnicianID", 0)
    }

}