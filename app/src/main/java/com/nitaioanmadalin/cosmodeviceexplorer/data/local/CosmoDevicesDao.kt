package com.nitaioanmadalin.cosmodeviceexplorer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nitaioanmadalin.cosmodeviceexplorer.data.local.entities.CosmoDeviceEntity

@Dao
interface CosmoDevicesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevices(repositories: List<CosmoDeviceEntity>)

    @Query("SELECT * FROM cosmodeviceentity")
    suspend fun getDevices(): List<CosmoDeviceEntity>
}