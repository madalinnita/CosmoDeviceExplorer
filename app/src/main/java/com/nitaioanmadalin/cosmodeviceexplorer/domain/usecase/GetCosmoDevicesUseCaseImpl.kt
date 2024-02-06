package com.nitaioanmadalin.cosmodeviceexplorer.domain.usecase

import com.nitaioanmadalin.cosmodeviceexplorer.core.utils.network.AppResult
import com.nitaioanmadalin.cosmodeviceexplorer.domain.model.CosmoDevice
import com.nitaioanmadalin.cosmodeviceexplorer.domain.repository.CosmoDevicesRepository
import kotlinx.coroutines.flow.Flow

class GetCosmoDevicesUseCaseImpl(
    val repository: CosmoDevicesRepository
): GetCosmoDevicesUseCase {
    override fun getCosmoDevices(): Flow<AppResult<List<CosmoDevice>>> {
        return repository.getCosmoDevices()
    }
}