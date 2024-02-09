package com.nitaioanmadalin.cosmodeviceexplorer.data.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import com.nitaioanmadalin.cosmodeviceexplorer.domain.bluetooth.BluetoothController
import com.nitaioanmadalin.cosmodeviceexplorer.domain.model.BluetoothDeviceItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@SuppressLint("MissingPermission")
class AndroidBluetoothController(
    val context: Context
) : BluetoothController {

    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }

    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val _scannedDevices = MutableStateFlow<List<BluetoothDeviceItem>>(emptyList())
    private val _pairedDevices = MutableStateFlow<List<BluetoothDeviceItem>>(emptyList())
    override val scannedDevices: StateFlow<List<BluetoothDeviceItem>>
        get() = _scannedDevices.asStateFlow()
    override val pairedDevices: StateFlow<List<BluetoothDeviceItem>>
        get() = _pairedDevices.asStateFlow()

    private val foundDeviceReceiver = FoundDeviceReceiver {device ->
        _scannedDevices.update { devices ->
            val newDevice = device.toBluetoothDeviceItem()
            if(newDevice in devices) devices else devices + newDevice
        }
    }

    init {
        updatePairedDevices()
    }
    override fun startScanning() {
        if(!hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            return
        }

        context.registerReceiver(
            foundDeviceReceiver,
            IntentFilter(BluetoothDevice.ACTION_FOUND)
        )

        updatePairedDevices()

        bluetoothAdapter?.startDiscovery()
    }

    override fun stopScanning() {
        if(!hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            return
        }

        bluetoothAdapter?.cancelDiscovery()
    }

    override fun release() {
        context.unregisterReceiver(foundDeviceReceiver)
    }

    private fun updatePairedDevices() {
        if(!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
            return
        }

        bluetoothAdapter?.bondedDevices?.map {
            it.toBluetoothDeviceItem()
        }?.also { newDevices ->
            _pairedDevices.update { newDevices }
        }
    }

    private fun hasPermission(permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
}