package com.nitaioanmadalin.cosmodeviceexplorer.ui.bluetooth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.nitaioanmadalin.cosmodeviceexplorer.domain.model.BluetoothDeviceItem
import com.nitaioanmadalin.cosmodeviceexplorer.presentation.bluetooth.BluetoothViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ScanBluetoothScreen(
    viewModel: BluetoothViewModel,
    navigator: NavController,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = state.errorMessage) {
        state.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(key1 = state.isConnected) {
        if (state.isConnected) {
            Toast.makeText(context, "You are connected.", Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            BluetoothTopBar(
                onBackButtonClicked = { navigator.popBackStack() }
            )
        }
    ) {
        Surface(
            modifier = Modifier.padding(it)
        ) {
            when {
                state.isConnecting -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Text(text = "Connecting...", modifier = Modifier.padding(16.dp))
                        Button(modifier = Modifier.padding(16.dp),
                            onClick = { viewModel.disconnectFromDevice() }) {
                            Text(text = "Cancel")
                        }
                    }
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Button(
                                modifier = Modifier.padding(4.dp),
                                onClick = viewModel::startScan
                            ) {
                                Text(text = "Start Scanning")
                            }
                            Button(
                                modifier = Modifier.padding(4.dp),
                                onClick = viewModel::stopScan
                            ) {
                                Text(text = "Stop Scanning")
                            }
                            Button(
                                modifier = Modifier.padding(4.dp),
                                onClick = viewModel::waitForIncomingConnections
                            ) {
                                Text(text = "Start server")
                            }
                        }
                        BluetoothDeviceList(
                            scannedDevices = state.scannedDevices,
                            pairedDevices = state.pairedDevices,
                            onClick = { device ->
                                Toast.makeText(
                                    context,
                                    "Mac address: ${device.macAddress}",
                                    Toast.LENGTH_LONG
                                ).show()
                                viewModel.connectToDevice(device)
                            })
                    }
                }
            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BluetoothTopBar(
    onBackButtonClicked: () -> Unit
) {
    val navigationIcon: @Composable () -> Unit = {
        IconButton(onClick = {
            onBackButtonClicked.invoke()
        }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
        }
    }

    TopAppBar(
        title = { Text("Bluetooth Hub", color = Color.Black) },
        navigationIcon = navigationIcon,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.LightGray,
            titleContentColor = Color.White
        )
    )
}