package com.example.readmatetasks.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmatetasks.R
import com.example.readmatetasks.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Representa los diferentes estados de la pantalla de inicio de sesión.
 */
sealed class LoginState {
    /** Estado inicial sin ninguna acción realizada. */
    data object Idle : LoginState()

    /** Estado de carga mientras se procesa la autenticación. */
    data object Loading : LoginState()

    /** Estado de éxito cuando el usuario inicia sesión correctamente. */
    data object Success : LoginState()

    /**
     * Estado de error con un mensaje asociado.
     *
     * @param messageKey Clave del recurso de cadena de error.
     */
    data class Error(val messageKey: Int) : LoginState()
}

/**
 * ViewModel para gestionar el inicio de sesión de los usuarios.
 *
 * @param authRepository Repositorio de autenticación para manejar las credenciales del usuario.
 */
class LoginViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    /**
     * Inicia sesión con las credenciales proporcionadas.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     */
    fun loginUser(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error(R.string.error_empty_fields)
            return
        }

        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            val resultado = authRepository.loginUser(email, password)
            _loginState.value = if (resultado.isSuccess) {
                LoginState.Success
            } else {
                val exception = resultado.exceptionOrNull()
                val errorMessage = when {
                    exception is FirebaseAuthException && exception.errorCode == "ERROR_INVALID_EMAIL" -> R.string.error_invalid_email
                    exception is FirebaseAuthException && exception.message?.contains("The supplied auth credential is incorrect") == true -> R.string.error_invalid_credentials
                    exception is FirebaseAuthException && exception.message?.contains("There is no user record") == true -> R.string.error_invalid_credentials
                    exception is FirebaseAuthException && exception.message?.contains("network error") == true -> R.string.error_server
                    else -> R.string.error_unknown
                }
                LoginState.Error(errorMessage)
            }
        }
    }
}
