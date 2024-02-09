package com.nitaioanmadalin.cosmodeviceexplorer.domain.bluetooth

import com.nitaioanmadalin.cosmodeviceexplorer.domain.model.BluetoothDeviceItem
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {
    val scannedDevices: StateFlow<List<BluetoothDeviceItem>>
    val pairedDevices: StateFlow<List<BluetoothDeviceItem>>

    fun startScanning()
    fun stopScanning()

    fun release()
}