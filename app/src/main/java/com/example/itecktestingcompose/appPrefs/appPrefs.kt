package com.example.itecktestingcompose.appPrefs

import android.content.Context
import androidx.core.content.edit

class PreferenceManager(context: Context) {

    private val sharedPref = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    fun setHasNewNotification(value: Boolean) {
        sharedPref.edit { putBoolean("hasNewNotification", value) }
    }

    fun getHasNewNotification(): Boolean {
        return sharedPref.getBoolean("hasNewNotification", false)
    }

    fun setTechnicianName(name: String) {
        sharedPref.edit { putString("TechnicianName", name) }
    }

    fun getTechnicianName(): String {
        return sharedPref.getString("TechnicianName", "") ?: ""
    }

    fun setUserCNIC(cnic: String) {
        sharedPref.edit { putString("UserCNIC", cnic) }
    }

    fun getUserCNIC(): String {
        return sharedPref.getString("UserCNIC", "") ?: ""
    }

    fun setLatitude(latitude: String) {
        sharedPref.edit { putString("latitude", latitude) }
    }

    fun getLatitude(): String {
        return sharedPref.getString("latitude", "") ?: ""
    }

    fun setLongitude(longitude: String) {
        sharedPref.edit { putString("longitude", longitude) }
    }

    fun getLongitude(): String {
        return sharedPref.getString("longitude", "") ?: ""
    }

    fun setAppLoginID(id: String) {
        sharedPref.edit { putString("AppLoginID", id) }
    }

    fun getAppLoginID(): String {
        return sharedPref.getString("AppLoginID", "") ?: ""
    }

}