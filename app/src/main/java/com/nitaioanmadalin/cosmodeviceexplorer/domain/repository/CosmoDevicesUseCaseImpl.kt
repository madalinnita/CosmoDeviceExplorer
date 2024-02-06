package com.nitaioanmadalin.cosmodeviceexplorer.domain.repository

import com.nitaioanmadalin.cosmodeviceexplorer.core.utils.network.AppResult
import com.nitaioanmadalin.cosmodeviceexplorer.data.remote.response.CosmoDevicesResponse
import com.nitaioanmadalin.cosmodeviceexplorer.domain.model.CosmoDevice
import com.nitaioanmadalin.cosmodeviceexplorer.domain.usecase.GetCosmoDevicesUseCase
import kotlinx.coroutines.flow.Flow

class CosmoDevicesUseCaseImpl(
    val repository: CosmoDevicesRepository
): GetCosmoDevicesUseCase {
    override fun getCosmoDevices(): Flow<AppResult<List<CosmoDevice>>> {
        return repository.getCosmoDevices()
    }
}