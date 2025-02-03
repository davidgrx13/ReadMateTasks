package com.example.readmatetasks.ui.screens.tasks

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import com.example.readmatetasks.data.model.Task
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import java.io.File
import java.text.SimpleDateFormat
import com.example.readmatetasks.R
import com.example.readmatetasks.ui.theme.*

/**
 * Pantalla que muestra la lista de tareas del usuario.
 *
 * @param viewModel ViewModel que maneja la obtención y manipulación de tareas.
 * @param onBack Acción para volver a la pantalla anterior.
 */

@Composable
fun TaskList(
    viewModel: TaskListViewModel,
    onBack: () -> Unit
) {
    val tasks by viewModel.tasks
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.downloadTasks()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        // Botón de volver
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(55.dp)
                    .padding(5.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back24),
                    contentDescription = stringResource(id = R.string.task_back_to_menu),
                    tint = MainTitleColor,
                    modifier = Modifier.size(30.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.task_list),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Black,
                    color = MainTitleColor,
                    fontSize = 34.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 6.dp),
                textAlign = TextAlign.Left
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Mostrar mensaje si no hay tareas disponibles
        if (tasks.isEmpty()) {
            Text(
                text = stringResource(id = R.string.task_no_available),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, false)
            ) {
                items(tasks) { tarea ->
                    TaskCard(
                        task = tarea,
                        doneTask = { id ->
                            viewModel.completeTask(id = id,
                                onSuccess = {
                                    Toast.makeText(
                                        context, context.getString(R.string.task_completed),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onFailure = { exception ->
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.task_error_completed) + exception.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                })
                        },
                        deleteTask = { id ->
                            viewModel.deleteTask(id = id, onSuccess = {
                                Toast.makeText(
                                    context, context.getString(R.string.task_deleted),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }, onFailure = { exception ->
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.task_error_deleted) + exception.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            })
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

/**
 * Tarjeta que representa una tarea individual en la lista.
 *
 * @param task Objeto de la tarea a mostrar.
 * @param doneTask Acción para marcar la tarea como completada.
 * @param deleteTask Acción para eliminar la tarea.
 */
@Composable
fun TaskCard(
    task: Task,
    doneTask: (String) -> Unit,
    deleteTask: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Mostrar imagen si existe
            if (!task.imagenPath.isNullOrEmpty()) {
                val file = File(task.imagenPath)
                if (file.exists()) {
                    Image(
                        painter = rememberAsyncImagePainter(file),
                        contentDescription = stringResource(id = R.string.task_image),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Text(
                text = task.titulo,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MainTitleColor
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = task.descripcion,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar fecha y hora de la tarea
            Text(
                text = stringResource(id = R.string.task_time) + " ${
                    SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault()).format(
                        Date(task.fechaHora)
                    )
                }",
                style = MaterialTheme.typography.bodySmall,
                color = LightGray
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Botones para completar o eliminar tarea
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { doneTask(task.id) },
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .background(AccentColor),
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.task_complete),
                        tint = SecondAccent
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                IconButton(
                    onClick = { deleteTask(task.id) },
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .background(OrangeError),
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.task_delete),
                        tint = SecondAccent
                    )
                }
            }
        }
    }
}

