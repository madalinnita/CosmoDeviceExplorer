package com.nitaioanmadalin.cosmodeviceexplorer.presentation.cosmodevices

import com.nitaioanmadalin.cosmodeviceexplorer.domain.model.CosmoDevice


sealed class CosmoDevicesScreenState {
    data class Loading(
        val daoRepositories: List<CosmoDevice>? = null
    ) : CosmoDevicesScreenState()

    data class Success(val repositories: List<CosmoDevice>) : CosmoDevicesScreenState()

    data class Error(
        val ex: Throwable,
        val isInternetAvailable: Boolean = true
    ) : CosmoDevicesScreenState()
}