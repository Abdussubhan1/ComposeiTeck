package com.example.itecktestingcompose.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeviceSearchHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val deviceNumber: String
)


