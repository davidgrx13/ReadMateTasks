package com.example.readmatetasks.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

// Esquema de colores para modo oscuro
private val DarkColorScheme = darkColorScheme(
    primary = AccentColor, // Se mantiene el color principal
    secondary = LightGray,
    tertiary = MainTitleColor,
    background = Color(0xFF121212), // Fondo oscuro realzado
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = Color.White, // Texto sobre fondo oscuro
    onSurface = Color.White
)

// Esquema de colores para modo claro
private val LightColorScheme = lightColorScheme(
    primary = AccentColor,
    secondary = LightGray,
    tertiary = MainTitleColor,
    background = BackgroundColor, // Fondo personalizado
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.Black, // Texto sobre fondo claro
    onSurface = Color.Black
)

@Composable
fun ReadMateTasksTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true, // Opcional: Dynamic Colors en Android 12+
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
        typography = Typography, // Mantiene la tipografía personalizada
        content = content
    )
}
