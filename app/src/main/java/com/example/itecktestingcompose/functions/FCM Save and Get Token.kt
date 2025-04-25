package com.example.itecktestingcompose.functions

import android.content.Context
import androidx.core.content.edit

fun saveTokenLocally(context: Context, token: String) {
    val prefs = context.getSharedPreferences("fcm_prefs", Context.MODE_PRIVATE)
    prefs.edit() { putString("fcm_token", token) }
}

fun getSavedToken(context: Context): String? {
    val prefs = context.getSharedPreferences("fcm_prefs", Context.MODE_PRIVATE)
    return prefs.getString("fcm_token", null)
}
