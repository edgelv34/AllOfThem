package com.lucky.allofthem.ui.component

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.lucky.allofthem.R


@Composable
fun Loading(
    modifier: Modifier = Modifier.fillMaxSize()
) {
    Box(
        modifier = modifier
            .background(Color.Transparent)
            .clickable(enabled = false) {},
        contentAlignment = Alignment.Center
    ) {
        LoadingImage()
    }
}


@Composable
fun LoadingImage(
    rotated: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val lifecycleOwner = LocalLifecycleOwner.current
    var isVisible by remember { mutableStateOf(true) }

    val colors = listOf(
        Color(0xFFFF0000), // 빨강
        Color(0xFFFF9900), // 주황
        Color(0xFFFFFF00), // 노랑
        Color(0xFF00FF00), // 초록
        Color(0xFF0000FF), // 파랑
        Color(0xFF4B0082), // 남색
        Color(0xFF800080)  // 보라색
    )

    // 색상 애니메이션
    val color by infiniteTransition.animateColor(
        initialValue = colors[0],
        targetValue = colors[colors.size - 1],
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = colors.size * 300 // 각 색상당 1초
                colors.forEachIndexed { index, color ->
                    color at (index * 300) // 색상 전환 시점
                }
            },
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            isVisible = event == Lifecycle.Event.ON_RESUME
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    Image(
        imageVector = ImageVector.vectorResource(R.drawable.ic_refresh),
        contentDescription = "로딩 중",
        modifier = Modifier
            .size(36.dp)
            .graphicsLayer(rotationZ = if (rotated && isVisible) rotation else 0f),
        colorFilter = ColorFilter.tint(color)
    )
}