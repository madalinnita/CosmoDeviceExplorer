package com.nitaioanmadalin.cosmodeviceexplorer.domain.model

data class CosmoDevice(
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
)