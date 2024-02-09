package com.nitaioanmadalin.cosmodeviceexplorer.ui.bluetooth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.nitaioanmadalin.cosmodeviceexplorer.core.utils.lifecycle.LifecycleHandler
import com.nitaioanmadalin.cosmodeviceexplorer.domain.model.BluetoothDeviceItem
import com.nitaioanmadalin.cosmodeviceexplorer.presentation.bluetooth.BluetoothViewModel

@Composable
fun ScanBluetoothScreen(
    viewModel: BluetoothViewModel,
    navigator: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Surface {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = viewModel::startScan) {
                    Text(text = "Start Scanning")
                }
                Button(onClick = viewModel::stopScan) {
                    Text(text = "Stop Scanning")
                }
            }
            BluetoothDeviceList(
                scannedDevices = state.scannedDevices,
                pairedDevices = state.pairedDevices,
                onClick = { device ->

                })
        }

    }
}

@Composable
private fun BluetoothDeviceList(
    scannedDevices: List<BluetoothDeviceItem>,
    pairedDevices: List<BluetoothDeviceItem>,
    onClick: (BluetoothDeviceItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            Text(
                text = "Paired devices:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(pairedDevices.size) {
            val pairedDevice = pairedDevices[it]
            Text(
                text = pairedDevice.name ?: "No name",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        onClick.invoke(pairedDevice)
                    },
            )
        }
        item {
            Text(
                text = "Scanned devices:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(scannedDevices.size) {
            val scannedDevice = scannedDevices[it]
            Text(
                text = scannedDevice.name ?: "No name",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        onClick.invoke(scannedDevice)
                    },
            )
        }
    }

}