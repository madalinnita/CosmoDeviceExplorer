package com.nitaioanmadalin.cosmodeviceexplorer.domain.usecase.getdevices

import com.nitaioanmadalin.cosmodeviceexplorer.core.utils.network.AppResult
import com.nitaioanmadalin.cosmodeviceexplorer.data.remote.response.CosmoDevicesResponse
import com.nitaioanmadalin.cosmodeviceexplorer.domain.model.CosmoDevice
import kotlinx.coroutines.flow.Flow

interface GetCosmoDevicesUseCase {
    fun getCosmoDevices(): Flow<AppResult<List<CosmoDevice>>>
}