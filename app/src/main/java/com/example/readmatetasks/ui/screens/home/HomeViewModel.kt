package com.example.readmatetasks.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.readmatetasks.ui.session.AuthSessionViewModel

/**
 * ViewModel para la pantalla de inicio.
 *
 * @param authSessionViewModel ViewModel que gestiona la sesión del usuario.
 */
class HomeViewModel(private val authSessionViewModel: AuthSessionViewModel) : ViewModel() {

    /**
     * Cierra la sesión del usuario.
     */
    fun logout() {
        authSessionViewModel.logout()
    }
}
