package com.example.itecktestingcompose.functions

import android.content.Context
import com.example.itecktestingcompose.appPrefs.PreferenceManager

fun getSavedToken(prefs: PreferenceManager): String {
    return prefs.getFCM()
}
