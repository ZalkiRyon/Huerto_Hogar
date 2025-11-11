package com.example.huerto_hogar.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    // green for buttons
    primary = Emerald,
    onPrimary = Color.White,
    // background cards
    surface = SurfaceDark,
    // background screen
    background = BackgroundDark,
    //  principal text
    onSurface = PrimaryTextDark,
    onBackground = PrimaryTextDark,

    surfaceVariant = SurfaceDark.copy(alpha = 0.8f),
    onSurfaceVariant = SecondaryTextDark,
    outline = SecondaryTextDark.copy(alpha = 0.5f)
)

private val LightColorScheme = lightColorScheme(
    // Green for buttons
    primary = Emerald,
    // text over green
    onPrimary = Color.White,
    // cards
    surface = SurfaceLight,
    // background screen
    background = BackgroundLight,
    // principal text over surface
    onSurface = PrimaryTextLight,
    // principal text over background
    onBackground = PrimaryTextLight,

    surfaceVariant = Color(0xFFE8E8E8),
    onSurfaceVariant = SecondaryTextLight,
    outline = SecondaryTextLight.copy(alpha = 0.3f)


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun Huerto_HogarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}