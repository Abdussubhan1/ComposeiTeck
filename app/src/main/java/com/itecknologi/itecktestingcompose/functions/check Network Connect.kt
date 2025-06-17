package com.itecknologi.itecktestingcompose.functions

fun isInternetAvailable(): Boolean {

    return try {
        val process = Runtime.getRuntime().exec("ping -c 1 8.8.8.8")
        val exitCode = process.waitFor()
        exitCode == 0  // ✅ Returns true only if ping was successful
    } catch (e: Exception) {
        false
    }
}
