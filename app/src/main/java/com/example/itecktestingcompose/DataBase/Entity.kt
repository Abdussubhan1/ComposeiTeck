package com.example.itecktestingcompose.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeviceSearchHistory(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo("Device_numbers") val deviceNumber: String
)


