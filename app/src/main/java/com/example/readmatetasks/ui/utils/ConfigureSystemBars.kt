package com.example.readmatetasks.ui.utils

import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.SideEffect

/**
 * Configura los colores de las barras del sistema (barra de estado y barra de navegación).
 *
 * Establece el color de fondo en blanco y los iconos en modo oscuro.
 */
@Composable
fun ConfigureSystemBars() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = true

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.White, // Color de las barras del sistema
            darkIcons = useDarkIcons // Usa iconos oscuros para mayor visibilidad
        )
    }
}
