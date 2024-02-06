package com.nitaioanmadalin.cosmodeviceexplorer.data.remote.response

import com.nitaioanmadalin.cosmodeviceexplorer.data.remote.dto.CosmoDeviceDto

data class CosmoDevicesResponse(
    val devices: List<CosmoDeviceDto?>?
)