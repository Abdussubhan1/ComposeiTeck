package com.itecknologi.itecktestingcompose.functions

import android.content.Context
import android.content.pm.PackageManager
import com.itecknologi.itecktestingcompose.appPrefs.PreferenceManager
import com.itecknologi.itecktestingcompose.constants.Constants

fun getAppVersion(context: Context, prefs: PreferenceManager): String {
    return try {
        val packageInfo= context.packageManager.getPackageInfo(context.packageName, 0)
        prefs.setAppversion(packageInfo.versionName ?: "0")
        packageInfo.versionName ?: ""

    } catch (e: PackageManager.NameNotFoundException) {
        "N/A"
    }
}