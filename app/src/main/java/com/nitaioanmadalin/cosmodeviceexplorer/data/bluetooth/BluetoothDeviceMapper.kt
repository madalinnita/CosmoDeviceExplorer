package com.nitaioanmadalin.cosmodeviceexplorer.data.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.nitaioanmadalin.cosmodeviceexplorer.domain.model.BluetoothDeviceItem

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceItem(): BluetoothDeviceItem {
    return BluetoothDeviceItem(
        name = this.name,
        macAddress = this.address
    )
}