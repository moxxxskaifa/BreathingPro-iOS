package com.breathing.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.breathing.app.platform.HapticFeedback
import kotlinx.coroutines.delay

@Composable
fun MainScreen() {
    var phase by remember { mutableStateOf("Inhale") }
    var isActive by remember { mutableStateOf(false) }
    val haptic = remember { HapticFeedback() }

    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.4f,
        animationSpec = infiniteRepeatable(tween(4000), RepeatMode.Reverse)
    )

    LaunchedEffect(isActive) {
        if (isActive) {
            while (true) {
                phase = "Inhale"; delay(4000); haptic.light()
                phase = "Hold"; delay(4000); haptic.light()
                phase = "Exhale"; delay(4000); haptic.light()
                phase = "Rest"; delay(4000); haptic.light()
            }
        }
    }

    Box(Modifier.fillMaxSize().background(Color(0xFFF0F4FF)), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.size(240.dp), contentAlignment = Alignment.Center) {
                Canvas(Modifier.fillMaxSize()) {
                    val r = size.minDimension / 2
                    drawCircle(Color(0xFF4A90D9).copy(alpha = 0.08f), r * 1.5f, center = Offset(size.width/2, size.height/2))
                    drawCircle(Color(0xFF4A90D9).copy(alpha = 0.12f), r * if (isActive) 1.3f else 1f, center = Offset(size.width/2, size.height/2))
                    drawCircle(Color(0xFF4A90D9).copy(alpha = if (isActive) 0.2f else 0.15f), r * if (isActive) scale else 1f, center = Offset(size.width/2, size.height/2))
                }
                Text(phase, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4A90D9))
            }
            Spacer(Modifier.height(40.dp))
            Text("4-4-4-4 Breathing", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Text("Inhale 4s · Hold 4s · Exhale 4s · Rest 4s", fontSize = 13.sp, color = Color.Gray)
            Spacer(Modifier.height(24.dp))
            Button(onClick = { isActive = !isActive }, modifier = Modifier.height(56.dp).width(200.dp),
                   shape = RoundedCornerShape(28.dp),
                   colors = ButtonDefaults.buttonColors(containerColor = if (isActive) Color(0xFFE74C3C) else Color(0xFF4A90D9))) {
                Text(if (isActive) "Stop" else "Start Session", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
