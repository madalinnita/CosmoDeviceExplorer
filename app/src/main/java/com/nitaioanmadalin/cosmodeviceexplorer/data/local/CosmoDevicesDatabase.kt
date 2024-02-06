package com.nitaioanmadalin.cosmodeviceexplorer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nitaioanmadalin.cosmodeviceexplorer.data.local.entities.CosmoDeviceEntity

@Database(
    entities = [CosmoDeviceEntity::class],
    version = 1
)
abstract class CosmoDevicesDatabase: RoomDatabase() {
    abstract val dao: CosmoDevicesDao
}