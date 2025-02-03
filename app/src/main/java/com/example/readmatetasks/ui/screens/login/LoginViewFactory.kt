package com.example.readmatetasks.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readmatetasks.data.repository.AuthRepository

/**
 * Factory para crear instancias de [LoginViewModel] con un [AuthRepository] inyectado.
 *
 * @param authRepository Repositorio de autenticación que maneja el inicio de sesión.
 */
class LoginViewModelFactory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {

    /**
     * Crea una instancia de [LoginViewModel] si la clase coincide.
     *
     * @param modelClass Clase del ViewModel a crear.
     * @return Instancia de [LoginViewModel].
     * @throws IllegalArgumentException Si la clase no es compatible.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
