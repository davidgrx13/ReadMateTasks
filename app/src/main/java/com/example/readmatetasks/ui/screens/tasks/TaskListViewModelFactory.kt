package com.example.readmatetasks.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readmatetasks.data.repository.AuthRepository
import com.example.readmatetasks.data.repository.TaskRepository

/**
 * Factory para crear instancias de [TaskListViewModel] con [TaskRepository] y [AuthRepository] inyectados.
 *
 * @param taskRepository Repositorio de tareas para gestionar la lista de tareas del usuario.
 * @param authRepository Repositorio de autenticación para obtener el usuario actual.
 */
class TaskListViewModelFactory(
    private val taskRepository: TaskRepository,
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {

    /**
     * Crea una instancia de [TaskListViewModel] si la clase coincide.
     *
     * @param modelClass Clase del ViewModel a crear.
     * @return Instancia de [TaskListViewModel].
     * @throws IllegalArgumentException Si la clase no es compatible.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
            return TaskListViewModel(taskRepository, authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
