package com.nitaioanmadalin.cosmodeviceexplorer.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CosmoDevice(
    val macAddress: String,
    val model: String? = null,
    val product: String? = null,
    val firmwareVersion: String? = null,
    val serial: String? = null,
    val installationMode: String? = null,
    val brakeLight: Boolean? = null,
    val lightMode: String? = null,
    val lightAuto: Boolean? = null,
    val lightValue: Int? = null
): Parcelable