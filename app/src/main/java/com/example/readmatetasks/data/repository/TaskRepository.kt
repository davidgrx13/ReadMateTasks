package com.example.readmatetasks.data.repository

import android.content.Context
import com.example.readmatetasks.data.model.Task
import com.google.firebase.Timestamp
import java.util.Date
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Repositorio para gestionar las tareas en Firestore y el almacenamiento de imágenes local.
 *
 * @property context Contexto de la aplicación para acceder al almacenamiento interno.
 * @property firestore Instancia de FirebaseFirestore para la gestión de tareas.
 */
class TaskRepository(
    private val context: Context,
    private val firestore: FirebaseFirestore
) {

    /**
     * Guarda una imagen en el almacenamiento interno del dispositivo.
     *
     * @param imagen Bitmap de la imagen a guardar.
     * @param name Nombre con el que se guardará el archivo.
     * @return Ruta absoluta del archivo guardado.
     */
    fun saveImg(imagen: Bitmap, name: String): String {
        val file = File(context.filesDir, "$name.jpg") // Almacenamiento interno
        FileOutputStream(file).use { fos ->
            imagen.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        }
        return file.absolutePath
    }

    /**
     * Añade una nueva tarea a Firestore.
     *
     * @param titulo Título de la tarea.
     * @param descripcion Descripción de la tarea.
     * @param fecha Fecha programada de la tarea.
     * @param imagenPath Ruta de la imagen asociada (opcional).
     * @param userId ID del usuario dueño de la tarea.
     */
    suspend fun addTask(
        titulo: String,
        descripcion: String,
        fecha: Date,
        imagenPath: String?,
        userId: String
    ) {
        val tareaId = firestore.collection("Tasks").document().id

        val tareaData = mutableMapOf(
            "id" to tareaId,
            "titulo" to titulo,
            "descripcion" to descripcion,
            "completada" to false,
            "fecha" to Timestamp(fecha),
            "userId" to userId
        )
        if (imagenPath != null) {
            tareaData["imagenPath"] = imagenPath
        }

        firestore.collection("Tasks").document(tareaId).set(tareaData).await()
    }

    /**
     * Marca una tarea como completada en Firestore.
     *
     * @param id Identificador de la tarea.
     */
    suspend fun completedTask(id: String) {
        try {
            firestore.collection("Tasks")
                .document(id)
                .update("completada", true)
                .await()
        } catch (_: Exception) {
            throw Exception()
        }
    }

    /**
     * Elimina una tarea de Firestore y borra la imagen asociada si existe.
     *
     * @param id Identificador de la tarea a eliminar.
     */
    suspend fun deleteTask(id: String) {
        val documento = firestore.collection("Tasks").document(id).get().await()
        if (documento.exists()) {
            val imagenPath = documento.getString("imagenPath")
            if (!imagenPath.isNullOrEmpty()) {
                val archivo = File(imagenPath)
                if (archivo.exists()) {
                    archivo.delete()
                }
            }
        }
        firestore.collection("Tasks").document(id).delete().await()
    }

    /**
     * Descarga las tareas de un usuario desde Firestore.
     *
     * @param userId ID del usuario dueño de las tareas.
     * @param onSuccess Callback con la lista de tareas si la descarga es exitosa.
     * @param onFailure Callback con la excepción si ocurre un error.
     */
    fun downloadTasks(
        userId: String,
        onSuccess: (List<Task>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("Tasks")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { snapshot ->
                val tasks = snapshot.documents.mapNotNull { doc ->
                    val id = doc.getString("id") ?: return@mapNotNull null
                    val titulo = doc.getString("titulo") ?: return@mapNotNull null
                    val descripcion = doc.getString("descripcion") ?: return@mapNotNull null
                    val completada = doc.getBoolean("completada") ?: false
                    val fecha = doc.getTimestamp("fecha")?.toDate() ?: Date()
                    val imagenPath = doc.getString("imagenPath")
                    Task(
                        id = id,
                        titulo = titulo,
                        descripcion = descripcion,
                        completada = completada,
                        fechaHora = fecha.time,
                        imagenPath = imagenPath,
                        userId = userId
                    )
                }
                onSuccess(tasks)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}
