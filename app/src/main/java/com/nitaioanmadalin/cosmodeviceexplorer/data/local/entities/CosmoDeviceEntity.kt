package com.nitaioanmadalin.cosmodeviceexplorer.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nitaioanmadalin.cosmodeviceexplorer.domain.model.CosmoDevice

@Entity
data class CosmoDeviceEntity(
    @PrimaryKey val macAddress: String = "-1",
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
    fun toCosmoDevice(): CosmoDevice = CosmoDevice(
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