package com.example.readmatetasks.ui.screens.tasks

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.readmatetasks.data.model.Task
import com.example.readmatetasks.data.repository.TaskRepository
import com.example.readmatetasks.data.repository.AuthRepository

/**
 * ViewModel para manejar la lista de tareas del usuario.
 *
 * @param repository Repositorio de tareas para obtener, actualizar y eliminar tareas.
 * @param authRepository Repositorio de autenticación para obtener el usuario actual.
 */
class TaskListViewModel(
    private val repository: TaskRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _tasks = mutableStateOf<List<Task>>(emptyList())
    val tasks: State<List<Task>> get() = _tasks

    private val _errorMessage = mutableStateOf<String?>(null)

    /**
     * Descarga la lista de tareas del usuario actual desde Firestore.
     */
    fun downloadTasks() {
        viewModelScope.launch {
            try {
                val userId = authRepository.getCurrentUserId()
                    ?: throw Exception("No se pudo obtener el ID del usuario.")
                repository.downloadTasks(
                    userId = userId,
                    onSuccess = { tasks ->
                        _tasks.value = tasks
                    },
                    onFailure = { exception ->
                        _errorMessage.value = exception.message
                    }
                )
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    /**
     * Elimina una tarea específica y actualiza la lista de tareas.
     *
     * @param id ID de la tarea a eliminar.
     * @param onSuccess Acción a ejecutar si la tarea se elimina correctamente.
     * @param onFailure Acción a ejecutar si ocurre un error en la eliminación.
     */
    fun deleteTask(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                repository.deleteTask(id)
                onSuccess()
                downloadTasks() // Actualiza la lista tras eliminar la tarea
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    /**
     * Marca una tarea como completada y actualiza la lista de tareas.
     *
     * @param id ID de la tarea a marcar como completada.
     * @param onSuccess Acción a ejecutar si la tarea se completa correctamente.
     * @param onFailure Acción a ejecutar si ocurre un error al marcar la tarea.
     */
    fun completeTask(
        id: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                repository.completedTask(id)
                onSuccess()
                downloadTasks() // Actualiza la lista tras completar la tarea
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }
}


