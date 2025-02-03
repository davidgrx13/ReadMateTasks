package com.example.readmatetasks.ui.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readmatetasks.data.repository.AuthRepository

/**
 * Factory para crear instancias de [AuthSessionViewModel] con un [AuthRepository] inyectado.
 *
 * @param authRepository Repositorio de autenticación que maneja la sesión del usuario.
 */
class AuthSessionViewModelFactory(private val authRepository: AuthRepository = AuthRepository()) : ViewModelProvider.Factory {

    /**
     * Crea una instancia de [AuthSessionViewModel] si la clase coincide.
     *
     * @param modelClass Clase del ViewModel a crear.
     * @return Instancia de [AuthSessionViewModel].
     * @throws IllegalArgumentException Si la clase no es compatible.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthSessionViewModel::class.java)) {
            return AuthSessionViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
