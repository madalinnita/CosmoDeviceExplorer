package com.nitaioanmadalin.cosmodeviceexplorer.presentation.bluetooth

import com.nitaioanmadalin.cosmodeviceexplorer.domain.model.BluetoothDeviceItem

data class BluetoothUiState(
 val scannedDevices: List<BluetoothDeviceItem> = emptyList(),
 val pairedDevices: List<BluetoothDeviceItem> = emptyList(),
 val isConnected: Boolean = false,
 val isConnecting: Boolean = false,
 val errorMessage: String? = null
)