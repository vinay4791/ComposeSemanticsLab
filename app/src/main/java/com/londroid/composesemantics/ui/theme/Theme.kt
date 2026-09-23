package com.londroid.composesemantics.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Palette matches the talk's slide deck: dark navy background, teal accent.
val AccentTeal = Color(0xFF2ED9C3)
val AccentGold = Color(0xFFFFD166)
val AccentRed = Color(0xFFFF6B6B)
val BackgroundNavy = Color(0xFF0F172A)
val SurfacePanel = Color(0xFF18233B)
val SurfacePanelBorder = Color(0xFF2B3A57)
val TextPrimary = Color(0xFFF5F7FA)
val TextMuted = Color(0xFF8B95A5)

private val LabDarkColors = darkColorScheme(
    primary = AccentTeal,
    onPrimary = Color(0xFF00201C),
    secondary = AccentGold,
    background = BackgroundNavy,
    onBackground = TextPrimary,
    surface = SurfacePanel,
    onSurface = TextPrimary,
    surfaceVariant = SurfacePanelBorder,
    onSurfaceVariant = TextMuted,
    error = AccentRed,
)

private val LabLightColors = lightColorScheme(
    primary = Color(0xFF0F766E),
    secondary = Color(0xFFB45309),
    background = Color(0xFFF7F9FC),
    surface = Color.White,
    error = Color(0xFFB3261E),
)

@Composable
fun ComposeSemanticsLabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) LabDarkColors else LabLightColors
    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
