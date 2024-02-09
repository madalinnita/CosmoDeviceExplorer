package com.nitaioanmadalin.cosmodeviceexplorer.ui.cosmodevices.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nitaioanmadalin.cosmodeviceexplorer.core.utils.lifecycle.LifecycleHandler
import com.nitaioanmadalin.cosmodeviceexplorer.domain.model.CosmoDevice
import com.nitaioanmadalin.cosmodeviceexplorer.presentation.cosmodevices.CosmoDevicesScreenState
import com.nitaioanmadalin.cosmodeviceexplorer.presentation.cosmodevices.CosmoDevicesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevicesScreen(
    viewModel: CosmoDevicesViewModel,
    onDeviceClicked: (CosmoDevice) -> Unit,
    onBluetoothScanClicked: () -> Unit
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LifecycleHandler(
        onCreate = { viewModel.getData(context = context) }
    )

    Scaffold(
        topBar = {
            DevicesTopBar()
        },
        bottomBar = {
            DevicesBottomBar(
                onScanDevicesClicked = {
                    onBluetoothScanClicked.invoke()
                }
            )
        }
    ) { paddingValues ->
        Surface(Modifier.padding(paddingValues)) {
            when (val state = viewState) {
                is CosmoDevicesScreenState.Error -> ErrorScreen(
                    onRetry = { viewModel.getData(context) },
                    isInternetConnectionAvailable = state.isInternetAvailable
                )

                is CosmoDevicesScreenState.Loading -> LoadingScreen()
                is CosmoDevicesScreenState.Success -> SuccessScreen(
                    data = state.repositories,
                    onDeviceClicked = onDeviceClicked
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevicesTopBar() {
    TopAppBar(
        title = { Text("List of devices", color = Color.Black) },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.LightGray,
            titleContentColor = Color.White
        )
    )
}

@Composable
fun DevicesBottomBar(
    onScanDevicesClicked: () -> Unit
) {
    BottomAppBar(
        containerColor = Color.LightGray,
    ) {
        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                width = 2.dp,
                color = Color.DarkGray,
                shape = MaterialTheme.shapes.medium
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
            ),
            onClick = {
                onScanDevicesClicked.invoke()
            }) {
            Text("Scan devices", color = Color.Black, fontSize = 20.sp)
        }
    }
}