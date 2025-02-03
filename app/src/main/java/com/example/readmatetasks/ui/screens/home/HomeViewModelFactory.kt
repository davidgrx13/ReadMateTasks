package com.example.readmatetasks.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readmatetasks.ui.session.AuthSessionViewModel

/**
 * Factory para crear instancias de [HomeViewModel] con un [AuthSessionViewModel] inyectado.
 *
 * @param authSessionViewModel ViewModel que gestiona la sesión del usuario.
 */
class HomeViewModelFactory(private val authSessionViewModel: AuthSessionViewModel) : ViewModelProvider.Factory {

    /**
     * Crea una instancia de [HomeViewModel] si la clase coincide.
     *
     * @param modelClass Clase del ViewModel a crear.
     * @return Instancia de [HomeViewModel].
     * @throws IllegalArgumentException Si la clase no es compatible.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(authSessionViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
