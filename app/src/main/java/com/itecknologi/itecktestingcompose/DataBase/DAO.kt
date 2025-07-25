package com.itecknologi.itecktestingcompose.DataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceSearchHistoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(deviceNumber: DeviceSearchHistory)

    @Query("DELETE FROM DeviceSearchHistory")
     suspend fun deleteAll()

    @Query("SELECT * FROM DeviceSearchHistory ORDER BY id DESC")
    suspend  fun getAllHistory(): List<DeviceSearchHistory>

}
