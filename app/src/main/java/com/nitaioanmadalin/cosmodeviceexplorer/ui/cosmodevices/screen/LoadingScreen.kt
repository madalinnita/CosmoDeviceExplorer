package com.nitaioanmadalin.cosmodeviceexplorer.ui.cosmodevices.screen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nitaioanmadalin.cosmodeviceexplorer.ui.theme.CosmoDeviceExplorerTheme

@Composable
fun LoadingScreen() {
    val shimmerColors = listOf(
        Color.DarkGray.copy(alpha = 0.2f),
        Color.LightGray,
        Color.DarkGray.copy(alpha = 0.2f)
    )
    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 3000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Column(modifier = Modifier.padding(16.dp)) {
        repeat(10) {
            Card(modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(8.dp))) {
                Box(
                    modifier =
                    Modifier
                        .background(
                            Brush.horizontalGradient(
                                shimmerColors,
                                0f,
                                translateAnim.value
                            )
                        )
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Box(modifier = Modifier
                            .height(20.dp)
                            .fillMaxWidth(0.6f)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Gray)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Gray)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun LoadingScreenPreview() {
    CosmoDeviceExplorerTheme {
        LoadingScreen()
    }
}