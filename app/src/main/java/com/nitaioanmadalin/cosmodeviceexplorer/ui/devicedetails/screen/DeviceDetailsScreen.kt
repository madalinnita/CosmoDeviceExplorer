package com.nitaioanmadalin.cosmodeviceexplorer.ui.devicedetails.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nitaioanmadalin.cosmodeviceexplorer.domain.model.CosmoDevice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceDetailsScreen(
    cosmoDevice: CosmoDevice,
    navigator: NavController
) {
    val properties = listOf(
        "MAC Address" to cosmoDevice.macAddress,
        "Model" to cosmoDevice.model,
        "Product" to cosmoDevice.product,
        "Firmware" to cosmoDevice.firmwareVersion,
        "Serial" to cosmoDevice.serial,
        "Installation Mode" to cosmoDevice.installationMode,
        "Brake Light" to (cosmoDevice.brakeLight?.let { if (it) "Enabled" else "Disabled" }),
        "Light Mode" to cosmoDevice.lightMode,
        "Light Auto" to (cosmoDevice.lightAuto?.let { if (it) "Enabled" else "Disabled" }),
        "Light Value" to cosmoDevice.lightValue?.toString()
    )

    Scaffold(
        topBar = {
            DeviceDetailsTopBar {
                navigator.popBackStack()
            }
        }
    ) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                items(properties) { property ->
                    PropertyCard(property = property)
                }
            }
        }
    }
}

@Composable
fun PropertyCard(property: Pair<String, String?>) {
    var isEnlarged by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .animateContentSize()
            .padding(if (isEnlarged) 16.dp else 8.dp)
            .clickable { isEnlarged = !isEnlarged }
            .heightIn(
                min = 100.dp,
                max = if (isEnlarged) 300.dp else 100.dp
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isEnlarged) 8.dp else 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = property.first,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            when (property.first) {
                "Brake Light", "Light Auto" -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (property.second == "Enabled") Icons.Filled.CheckCircle else Icons.Filled.Close,
                            contentDescription = property.second,
                            tint = if (property.second == "Enabled") Color.Green else Color.Red,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = property.second ?: "N/A",
                            color = if (property.second == "Enabled") Color.Green else Color.Red,
                            fontSize = 16.sp
                        )
                    }
                }

                "Light Value" -> {
                    val lightValue = property.second?.toFloatOrNull() ?: 0f
                    val progress = lightValue / 100
                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = Color.Blue,
                        trackColor = Color.LightGray
                    )
                    Text(
                        text = "${property.second}%",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 4.dp),
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                else -> {
                    Text(
                        text = property.second ?: "N/A",
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceDetailsTopBar(
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
        title = { Text("Device Details", color = Color.Black) },
        navigationIcon = navigationIcon,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.LightGray,
            titleContentColor = Color.White
        )
    )
}