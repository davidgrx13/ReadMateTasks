package com.example.readmatetasks.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.example.readmatetasks.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.firebase.Timestamp
import kotlinx.coroutines.tasks.await
import com.example.readmatetasks.R

/**
 * Representa los diferentes estados de la pantalla de registro.
 */
sealed class RegisterState {
    /** Estado inicial sin ninguna acción realizada. */
    data object Idle : RegisterState()

    /** Estado de carga mientras se procesa el registro. */
    data object Loading : RegisterState()

    /** Estado de éxito cuando el usuario se registra correctamente. */
    data object Success : RegisterState()

    /**
     * Estado de error con un mensaje asociado.
     *
     * @param messageKey Clave del recurso de cadena de error.
     */
    data class Error(val messageKey: Int) : RegisterState()
}

/**
 * ViewModel para gestionar el registro de nuevos usuarios.
 *
 * @param authRepository Repositorio de autenticación que maneja el registro del usuario.
 */
class RegisterViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()

    /**
     * Verifica si la contraseña es válida (mínimo 6 caracteres y al menos una mayúscula).
     */
    private fun isValidPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[A-Z]).{6,}$")
        return regex.matches(password)
    }

    /**
     * Verifica si el email es válido.
     */
    private fun isValidEmail(email: String): Boolean {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}\$")
        return regex.matches(email)
    }

    /**
     * Verifica si el nombre de usuario es válido (debe contener al menos dos palabras).
     */
    private fun isValidUsername(username: String): Boolean {
        if (username.isBlank()) return false
        val regex = Regex("^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)+\$")
        return regex.matches(username)
    }

    /**
     * Registra un nuevo usuario con el email, contraseña y nombre proporcionados.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @param username Nombre del usuario.
     */
    fun registerUser(email: String, password: String, username: String) {
        if (email.isBlank() || password.isBlank() || username.isBlank()) {
            _registerState.value = RegisterState.Error(R.string.error_empty_fields)
            return
        }
        if (!isValidEmail(email)) {
            _registerState.value = RegisterState.Error(R.string.error_invalid_email)
            return
        }
        if (!isValidUsername(username)) {
            _registerState.value = RegisterState.Error(R.string.error_invalid_username)
            return
        }
        if (!isValidPassword(password)) {
            _registerState.value = RegisterState.Error(R.string.error_invalid_password)
            return
        }

        _registerState.value = RegisterState.Loading
        viewModelScope.launch {
            val result = authRepository.registerUser(email, password, username)
            if (result.isSuccess) {
                val userId = authRepository.getCurrentUserId()
                if (userId != null) {
                    val saveResult = saveUserDetails(userId, username, email)
                    _registerState.value = if (saveResult) {
                        RegisterState.Success
                    } else {
                        RegisterState.Error(R.string.error_saving_user)
                    }
                } else {
                    _registerState.value = RegisterState.Error(R.string.error_getting_user_id)
                }
            } else {
                _registerState.value = RegisterState.Error(R.string.error_unknown)
            }
        }
    }

    /**
     * Guarda los detalles del usuario en Firestore.
     *
     * @param userId ID del usuario recién registrado.
     * @param username Nombre del usuario.
     * @param email Correo electrónico del usuario.
     * @return `true` si los datos se guardaron correctamente, `false` si hubo un error.
     */
    private suspend fun saveUserDetails(userId: String, username: String, email: String): Boolean {
        return try {
            val firestore = FirebaseFirestore.getInstance()
            val userData = mapOf(
                "id" to userId,
                "username" to username,
                "email" to email,
                "createdAt" to Timestamp.now()
            )
            firestore.collection("Users").document(userId).set(userData).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
