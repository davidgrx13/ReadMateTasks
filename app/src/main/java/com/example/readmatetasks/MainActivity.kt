package com.example.readmatetasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.readmatetasks.ui.theme.ReadMateTasksTheme
import androidx.navigation.compose.rememberNavController
import com.example.readmatetasks.ui.navigation.MainNavHost
import com.example.readmatetasks.ui.utils.ConfigureSystemBars

/**
 * Actividad principal de la aplicación ReadmateTasks.
 *
 * Configura la navegación y el tema de la app utilizando Jetpack Compose.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadMateTasksTheme {
                val navController = rememberNavController()
                ConfigureSystemBars();
                MainNavHost(navController = navController)
            }
        }
    }
}