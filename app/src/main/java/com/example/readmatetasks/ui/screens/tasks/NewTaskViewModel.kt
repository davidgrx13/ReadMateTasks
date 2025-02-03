package com.example.readmatetasks.ui.screens.tasks

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmatetasks.data.repository.TaskRepository
import com.example.readmatetasks.data.repository.AuthRepository
import java.util.Date
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel para la creación de nuevas tareas.
 *
 * @param repository Repositorio de tareas para gestionar el almacenamiento de nuevas tareas.
 * @param authRepository Repositorio de autenticación para obtener el usuario actual.
 */
class NewTaskViewModel(
    private val repository: TaskRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val date = MutableStateFlow<Long?>(null)

    /**
     * Guarda una imagen en el almacenamiento interno y devuelve su ruta.
     *
     * @param img Bitmap de la imagen a guardar.
     * @return Ruta del archivo guardado o `null` si ocurre un error.
     */
    fun saveImage(img: Bitmap): String? {
        return try {
            val file = "${System.currentTimeMillis()}"
            repository.saveImg(img, file)
        } catch (_: Exception) {
            null
        }
    }

    /**
     * Crea una nueva tarea con los datos proporcionados y la guarda en el repositorio.
     *
     * @param title Título de la tarea.
     * @param description Descripción de la tarea.
     * @param imgPath Ruta de la imagen asociada a la tarea (opcional).
     * @param onSuccess Acción a ejecutar si la tarea se guarda correctamente.
     * @param onFailure Acción a ejecutar si ocurre un error al guardar la tarea.
     */
    fun createNewTask(
        title: String,
        description: String,
        imgPath: String?,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val userId = authRepository.getCurrentUserId()
                    ?: throw Exception("No se pudo obtener el ID del usuario.")

                val date = date.value?.let { Date(it) } ?: Date()
                repository.addTask(
                    titulo = title,
                    descripcion = description,
                    fecha = date,
                    imagenPath = imgPath,
                    userId = userId
                )
                onSuccess()
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }
}
