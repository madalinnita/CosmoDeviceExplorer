package com.nitaioanmadalin.cosmodeviceexplorer.domain.bluetooth

import com.nitaioanmadalin.cosmodeviceexplorer.domain.model.BluetoothDeviceItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {
    val isConnected: StateFlow<Boolean>
    val scannedDevices: StateFlow<List<BluetoothDeviceItem>>
    val pairedDevices: StateFlow<List<BluetoothDeviceItem>>
    val errors: SharedFlow<String>
    fun startScanning()
    fun stopScanning()
    fun startBluetoothServer(): Flow<ConnectionResult>
    fun connectToDevice(device: BluetoothDeviceItem): Flow<ConnectionResult>
    fun closeConnection()
    fun release()
}