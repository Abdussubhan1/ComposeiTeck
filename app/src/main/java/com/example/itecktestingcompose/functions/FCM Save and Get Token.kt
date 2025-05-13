package com.example.itecktestingcompose.functions

import android.content.Context
import com.example.itecktestingcompose.appPrefs.PreferenceManager

fun saveTokenLocally(token: String, prefs: PreferenceManager) {

    prefs.setFCM(fcm = token)

}

fun getSavedToken(prefs: PreferenceManager): String {
    return prefs.getFCM()
}
