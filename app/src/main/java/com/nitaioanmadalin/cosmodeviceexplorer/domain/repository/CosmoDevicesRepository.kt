package com.nitaioanmadalin.cosmodeviceexplorer.domain.repository

import com.nitaioanmadalin.cosmodeviceexplorer.core.utils.network.AppResult
import com.nitaioanmadalin.cosmodeviceexplorer.domain.model.CosmoDevice
import kotlinx.coroutines.flow.Flow

interface CosmoDevicesRepository {
    fun getCosmoDevices(): Flow<AppResult<List<CosmoDevice>>>
}