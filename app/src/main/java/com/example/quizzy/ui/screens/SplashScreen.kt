package com.example.quizzy.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    onFinished: () -> Unit
) {

    // Gradient background colours
    val pink = Color(0xFFFF4FA3)
    val green = Color(0xFF32CB83)

    val gradient = Brush.linearGradient(
        colors = listOf(pink, green)
    )

    // Animation state
    val scale = remember { Animatable(0.3f) }
    val opacity = remember { Animatable(0f) }

    LaunchedEffect(Unit) {

        // Run zoom and fade animations together
        coroutineScope {
            launch {
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 950
                    )
                )
            }

            launch {
                opacity.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 950
                    )
                )
            }
        }

        // Keep splash visible briefly
        delay(1550)

        // Notify navigation that splash has finished
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .scale(scale.value)
                .alpha(opacity.value)
        ) {

            Text(
                text = "Quizzy",
                fontSize = 64.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "LEARN • PLAY • MASTER",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 2.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}
