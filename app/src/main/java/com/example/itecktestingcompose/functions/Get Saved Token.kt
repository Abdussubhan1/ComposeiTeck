package com.example.itecktestingcompose.functions

import com.example.itecktestingcompose.appPrefs.PreferenceManager

fun getSavedToken(prefs: PreferenceManager): String {
    return prefs.getFCM()
}
