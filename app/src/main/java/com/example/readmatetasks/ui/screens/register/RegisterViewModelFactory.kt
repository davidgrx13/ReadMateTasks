package com.example.readmatetasks.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readmatetasks.data.repository.AuthRepository

/**
 * Factory para crear instancias de [RegisterViewModel] con un [AuthRepository] inyectado.
 *
 * @param authRepository Repositorio de autenticación que maneja el registro del usuario.
 */
class RegisterViewModelFactory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {

    /**
     * Crea una instancia de [RegisterViewModel] si la clase coincide.
     *
     * @param modelClass Clase del ViewModel a crear.
     * @return Instancia de [RegisterViewModel].
     * @throws IllegalArgumentException Si la clase no es compatible.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
