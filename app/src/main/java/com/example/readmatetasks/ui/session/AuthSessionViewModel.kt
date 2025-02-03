package com.example.readmatetasks.ui.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmatetasks.data.repository.AuthRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel que maneja la sesión del usuario y la obtención de sus datos.
 *
 * @param authRepository Repositorio de autenticación para manejar la sesión del usuario.
 */
class AuthSessionViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _userEmail = MutableStateFlow<String?>(null)
    val userEmail: StateFlow<String?> = _userEmail.asStateFlow()

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username.asStateFlow()

    /**
     * Obtiene los datos del usuario actual desde Firestore.
     */
    fun fetchCurrentUser() {
        val userId = authRepository.getCurrentUserId()
        if (userId != null) {
            viewModelScope.launch {
                val result = authRepository.getUserDetails(userId)
                if (result.isSuccess) {
                    val userDetails = result.getOrNull()
                    _username.value = userDetails?.get("username") as? String
                    _userEmail.value = userDetails?.get("email") as? String
                } else {
                    _username.value = null
                    _userEmail.value = null
                }
            }
        }
    }

    /**
     * Cierra la sesión del usuario y limpia los datos almacenados en la sesión actual.
     */
    fun logout() {
        authRepository.logoutUser()
        _username.value = null
        _userEmail.value = null
    }
}
