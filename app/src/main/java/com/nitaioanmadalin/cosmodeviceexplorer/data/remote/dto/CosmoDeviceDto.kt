package com.nitaioanmadalin.cosmodeviceexplorer.data.remote.dto

import com.nitaioanmadalin.cosmodeviceexplorer.data.local.entities.CosmoDeviceEntity

data class CosmoDeviceDto(
    val macAddress: String,
    val model: String?,
    val product: String?,
    val firmwareVersion: String?,
    val serial: String?,
    val installationMode: String?,
    val brakeLight: Boolean?,
    val lightMode: String?,
    val lightAuto: Boolean?,
    val lightValue: Int?
) {
    fun toCosmoDeviceEntity() = CosmoDeviceEntity(
        macAddress = macAddress,
        model = model,
        product = product,
        firmwareVersion = firmwareVersion,
        serial = serial,
        installationMode = installationMode,
        brakeLight = brakeLight,
        lightAuto = lightAuto,
        lightMode = lightMode,
        lightValue = lightValue
    )
}