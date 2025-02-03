package com.example.readmatetasks.ui.navigation

/**
 * Representa las rutas de navegación dentro de la aplicación.
 *
 * @property route Cadena que identifica la ruta en la navegación.
 */
sealed class Routes(val route: String) {
    object Login : Routes("login")
    object Register : Routes("register")
    object Home : Routes("home")
    object ListadoTareas : Routes("listadoTareas")
    object CrearTarea : Routes("crearTarea")
}
