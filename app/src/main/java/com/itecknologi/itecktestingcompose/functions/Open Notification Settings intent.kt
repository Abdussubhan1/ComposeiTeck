package com.itecknologi.itecktestingcompose.functions

import android.content.Context
import android.content.Intent
import android.provider.Settings

fun openAppNotificationSettings(context: Context) {
    val intent = Intent().apply {
        action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
    }
    context.startActivity(intent)
}