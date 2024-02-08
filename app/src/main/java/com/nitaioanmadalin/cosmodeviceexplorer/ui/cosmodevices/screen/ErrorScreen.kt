package com.nitaioanmadalin.cosmodeviceexplorer.ui.cosmodevices.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nitaioanmadalin.cosmodeviceexplorer.R
import com.nitaioanmadalin.cosmodeviceexplorer.ui.theme.CosmoDeviceExplorerTheme

@Composable
fun ErrorScreen(
    onRetry: () -> Unit,
    isInternetConnectionAvailable: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.error_image),
                contentDescription = "Error",
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Oops, something went wrong...",
                style = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.secondary),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                if (isInternetConnectionAvailable) "We're having trouble loading the data. Please try again."
                else "Please check your internet connection and come back.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)
            )
        }
        Button(
            onClick = onRetry,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Retry", color = MaterialTheme.colorScheme.onPrimary, fontSize = 24.sp)
        }
    }
}

@Preview
@Composable
fun ErrorScreenPreview() {
    CosmoDeviceExplorerTheme {
        ErrorScreen(
            onRetry = {},
            isInternetConnectionAvailable = true
        )
    }
}

@Preview
@Composable
fun ErrorScreenNoInternetPreview() {
    CosmoDeviceExplorerTheme {
        ErrorScreen(
            onRetry = {},
            isInternetConnectionAvailable = false
        )
    }
}