package com.example.readmatetasks.data.model

/**
 * Representa una tarea en la aplicación.
 *
 * @property id Identificador único de la tarea. Generado por Firebase si no se proporciona.
 * @property titulo Título de la tarea.
 * @property descripcion Descripción detallada de la tarea.
 * @property completada Indica si la tarea ha sido completada o no.
 * @property imagenPath Ruta de la imagen asociada a la tarea (opcional).
 * @property fechaHora Marca de tiempo que indica cuándo se creó o programó la tarea.
 * @property userId Identificador del usuario al que pertenece la tarea.
 */
data class Task(
    val id: String = "",
    val titulo: String = "",
    val descripcion: String = "",
    val completada: Boolean = false,
    val imagenPath: String? = null,
    val fechaHora: Long = 0L,
    val userId: String
)
