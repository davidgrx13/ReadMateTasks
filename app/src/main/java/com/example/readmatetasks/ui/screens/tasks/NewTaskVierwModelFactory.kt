package com.example.readmatetasks.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readmatetasks.data.repository.AuthRepository
import com.example.readmatetasks.data.repository.TaskRepository

/**
 * Factory para crear instancias de [NewTaskViewModel] con [TaskRepository] y [AuthRepository] inyectados.
 *
 * @param repository Repositorio de tareas para gestionar la creación de nuevas tareas.
 * @param authRepository Repositorio de autenticación para obtener información del usuario.
 */
class NewTaskViewModelFactory(
    private val repository: TaskRepository,
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {

    /**
     * Crea una instancia de [NewTaskViewModel] si la clase coincide.
     *
     * @param modelClass Clase del ViewModel a crear.
     * @return Instancia de [NewTaskViewModel].
     * @throws IllegalArgumentException Si la clase no es compatible.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewTaskViewModel::class.java)) {
            return NewTaskViewModel(repository, authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
