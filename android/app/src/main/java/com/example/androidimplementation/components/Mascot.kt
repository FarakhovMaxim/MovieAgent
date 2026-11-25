package com.example.androidimplementation.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale

@Composable
fun Mascot(modifier: Modifier = Modifier) {
    Canvas(modifier) {
        val factor = size.minDimension / 40f
        val indigo = Color(0xFF33296E)
        val lavender = Color(0xFFC9BAFF)
        val gold = Color(0xFFFFC752)

        scale(factor, factor, pivot = Offset.Zero) {
            drawCircle(indigo, radius = 20f, center = Offset(20f, 20f))
            val lens = Path().apply {
                moveTo(24f, 20f)
                lineTo(34f, 16f)
                lineTo(34f, 29f)
                lineTo(24f, 25f)
                close()
            }
            drawPath(lens, gold)
            drawCircle(lavender, 5f, Offset(13f, 13f))
            drawCircle(lavender, 6f, Offset(24f, 12f))
            drawRoundRect(lavender, Offset(7f, 17f), Size(22f, 15f), CornerRadius(5f))
            drawCircle(indigo, 1.5f, Offset(13f, 13f))
            drawCircle(indigo, 2f, Offset(24f, 12f))
            drawCircle(indigo, 1.5f, Offset(12.5f, 22.5f))
            drawCircle(indigo, 1.5f, Offset(22.5f, 22.5f))
            val smile = Path().apply {
                moveTo(15f, 26f)
                quadraticTo(17.5f, 30f, 20f, 26f)
            }
            drawPath(smile, indigo, style = Stroke(width = 1.5f, cap = StrokeCap.Round))
        }
    }
}
