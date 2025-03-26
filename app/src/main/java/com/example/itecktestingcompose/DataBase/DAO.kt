package com.example.itecktestingcompose.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DeviceSearchHistoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(device: DeviceSearchHistory)

    @Query("SELECT * FROM DeviceSearchHistory ORDER BY id DESC")
    fun getAllHistory(): List<DeviceSearchHistory>

}
