package com.example.itecktestingcompose.modelClasses

class NotificationHistory : ArrayList<NotificationHistoryItem>()

data class NotificationHistoryItem(
    val Technicalnotificationid: String,
    val body: String,
    val cnic: String,
    val entrydate: Entrydate,
    val title: String
)
