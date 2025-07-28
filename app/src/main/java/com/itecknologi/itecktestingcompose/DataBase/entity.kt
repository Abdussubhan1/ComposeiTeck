package com.itecknologi.itecktestingcompose.DataBase


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeviceSearchHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val deviceNumber: String
)


