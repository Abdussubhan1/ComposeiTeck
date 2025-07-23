package com.itecknologi.itecktestingcompose.modelClasses

class NotificationHistory : ArrayList<NotificationHistoryItem>()

data class NotificationHistoryItem(
    val body: String?,
    val entrydate: String?,
    val title: String?
)