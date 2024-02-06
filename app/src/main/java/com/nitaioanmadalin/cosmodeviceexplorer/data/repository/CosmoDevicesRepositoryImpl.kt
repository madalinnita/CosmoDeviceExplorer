package com.nitaioanmadalin.cosmodeviceexplorer.data.repository

import com.nitaioanmadalin.cosmodeviceexplorer.core.utils.network.AppResult
import com.nitaioanmadalin.cosmodeviceexplorer.data.local.CosmoDevicesDao
import com.nitaioanmadalin.cosmodeviceexplorer.data.remote.api.CosmoDevicesApi
import com.nitaioanmadalin.cosmodeviceexplorer.domain.model.CosmoDevice
import com.nitaioanmadalin.cosmodeviceexplorer.domain.repository.CosmoDevicesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CosmoDevicesRepositoryImpl(
    private val dao: CosmoDevicesDao,
    private val api: CosmoDevicesApi
) : CosmoDevicesRepository {
    override fun getCosmoDevices(): Flow<AppResult<List<CosmoDevice>>> = flow {
        emit(AppResult.Loading())
        val daoRepositories = dao.getDevices()
        emit(AppResult.Loading(daoRepositories.map { it.toCosmoDevice() }))

        try {
            api.getDevices()
                .devices?.filterNotNull()?.map { it.toCosmoDeviceEntity() }?.let {
                    dao.insertDevices(it)
                }
            val updatedRepositories = dao.getDevices().map { it.toCosmoDevice() }
            emit(AppResult.Success(updatedRepositories))
        } catch (ex: Throwable) {
            emit(AppResult.Error(ex, ex.message))
        }
    }
}