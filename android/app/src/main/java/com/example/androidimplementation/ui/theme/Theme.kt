package com.example.androidimplementation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val ScreenPadding = 16.dp

private val LightColors = lightColorScheme(
    primary = Color(0xFF5046A3),
    onPrimary = Color.White,
    background = Color(0xFFF4F3F8),
    surface = Color(0xFFFFFBFF),
    surfaceVariant = Color(0xFFE9E5F2)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFC7BFFF),
    onPrimary = Color(0xFF272161),
    background = Color(0xFF15141B),
    surface = Color(0xFF1E1D26),
    surfaceVariant = Color(0xFF322E41)
)

@Composable
fun AndroidImplementationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
