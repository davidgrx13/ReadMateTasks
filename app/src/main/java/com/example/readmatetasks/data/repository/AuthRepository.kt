package com.example.readmatetasks.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import kotlinx.coroutines.tasks.await

/**
 * Repositorio de autenticación que maneja el registro, inicio de sesión
 * y recuperación de datos de usuario usando Firebase Authentication y Firestore.
 *
 * @property firebaseAuth Instancia de FirebaseAuth para gestionar la autenticación.
 * @property firestore Instancia de FirebaseFirestore para almacenar información del usuario.
 */
class AuthRepository(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    /**
     * Registra un nuevo usuario en Firebase Authentication y guarda sus datos en Firestore.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @param username Nombre de usuario.
     * @return [Result] con éxito si el registro fue exitoso, o un fallo con la excepción correspondiente.
     */
    suspend fun registerUser(email: String, password: String, username: String): Result<Unit> {
        return try {
            // Registrar el user en FirebaseAuth
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid
                ?: return Result.failure(Exception())

            val saveResult = saveUserDetails(userId, username, email)
            if (saveResult.isSuccess) {
                Result.success(Unit)
            } else {
                Result.failure(saveResult.exceptionOrNull() ?: Exception())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Inicia sesión con Firebase Authentication.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return [Result] con éxito si el inicio de sesión fue exitoso, o un fallo con la excepción correspondiente.
     */
    suspend fun loginUser(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Guarda los detalles del usuario en Firestore.
     *
     * @param userId Identificador único del usuario en Firebase.
     * @param username Nombre de usuario.
     * @param email Correo electrónico del usuario.
     * @return [Result] con éxito si los datos fueron guardados, o un fallo con la excepción correspondiente.
     */
    private suspend fun saveUserDetails(
        userId: String,
        username: String,
        email: String
    ): Result<Unit> {
        return try {
            val userData = mapOf(
                "id" to userId,
                "username" to username,
                "email" to email,
                "createdAt" to Timestamp.now(),
                "profileImageUrl" to "" // Imagen de perfil vacía por defecto
            )
            firestore.collection("Users").document(userId).set(userData).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Obtiene los detalles del usuario desde Firestore.
     *
     * @param userId Identificador único del usuario.
     * @return [Result] con un mapa de datos del usuario si existe, o un fallo si no se encuentra.
     */
    suspend fun getUserDetails(userId: String): Result<Map<String, Any>> {
        return try {
            val snapshot = firestore.collection("Users")
                .document(userId)
                .get()
                .await()
            if (snapshot.exists()) {
                Result.success(snapshot.data ?: emptyMap())
            } else {
                Result.failure(Exception())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Obtiene el ID del usuario actualmente autenticado.
     *
     * @return ID del usuario si hay sesión iniciada, o `null` si no hay usuario autenticado.
     */
    fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    /**
     * Cierra la sesión del usuario en Firebase Authentication.
     */
    fun logoutUser() {
        firebaseAuth.signOut()
    }
}
