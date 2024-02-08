package com.nitaioanmadalin.cosmodeviceexplorer.ui.cosmodevices.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nitaioanmadalin.cosmodeviceexplorer.domain.model.CosmoDevice
import com.nitaioanmadalin.cosmodeviceexplorer.ui.theme.CosmoDeviceExplorerTheme

@Composable
fun SuccessScreen(
    data: List<CosmoDevice>,
    onDeviceClicked: (CosmoDevice) -> Unit
) {
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(data.size) { index ->
            DeviceInfo(
                item = data[index],
                onDeviceClicked = onDeviceClicked
            )
        }
    }
}

@Preview
@Composable
fun SuccessScreenPreview() {
    CosmoDeviceExplorerTheme {
        val items = mutableListOf<CosmoDevice>()
        for (index in 1..10) {
            items.add(
                CosmoDevice(
                    macAddress = "MockMacAdress",
                    model = "MockModel"
                )
            )
        }
        SuccessScreen(data = items, onDeviceClicked = {})
    }
}

@Composable
fun DeviceInfo(
    item: CosmoDevice,
    onDeviceClicked: (CosmoDevice) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .clickable {
                onDeviceClicked.invoke(item)
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
        ) {
            Text(
                text = "Mac adress: ${item.macAddress}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Model: ${item.model ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
