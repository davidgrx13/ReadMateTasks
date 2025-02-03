package com.example.readmatetasks.ui.screens.tasks

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import com.example.readmatetasks.R
import com.example.readmatetasks.ui.theme.*

/**
 * Pantalla para crear una nueva tarea.
 *
 * @param viewModel ViewModel encargado de la gestión de tareas.
 * @param onBack Acción para volver a la pantalla anterior.
 */
@Composable
fun NewTask(
    viewModel: NewTaskViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imgPath by remember { mutableStateOf<String?>(null) }
    var date by remember { mutableStateOf<Long?>(null) }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            calendar.set(year, month, day)
            date = calendar.timeInMillis
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            date = calendar.timeInMillis
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            val savedPath = viewModel.saveImage(bitmap)
            if (savedPath != null) {
                imgPath = savedPath
                Toast.makeText(context, context.getString(R.string.task_image_success), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, context.getString(R.string.task_image_error), Toast.LENGTH_SHORT).show()
            }
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        // Botón de volver al menú
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(65.dp)
                    .padding(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back24),
                    contentDescription = stringResource(id = R.string.task_back_to_menu),
                    tint = MainTitleColor,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Card de form
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.task_create_title),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Black,
                            color = MainTitleColor
                        )
                    )

                    IconButton(
                        onClick = { launcher.launch() },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(50))
                            .background(AccentColor)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_camera24),
                            contentDescription = stringResource(id = R.string.task_capture_image),
                            tint = SecondAccent,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(stringResource(id = R.string.task_title)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = LightGray,
                        focusedIndicatorColor = LightGray,
                        cursorColor = LightGray,
                        unfocusedLabelColor = LightGray,
                        focusedLabelColor = LightGray,
                        disabledIndicatorColor = LightGray
                    )
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(id = R.string.task_description)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = LightGray,
                        focusedIndicatorColor = LightGray,
                        cursorColor = LightGray,
                        unfocusedLabelColor = LightGray,
                        focusedLabelColor = LightGray,
                        disabledIndicatorColor = LightGray
                    )
                )

                OutlinedTextField(
                    value = date?.let {
                        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        sdf.format(Date(it))
                    } ?: stringResource(id = R.string.task_no_date),
                    onValueChange = { },
                    label = { Text(stringResource(id = R.string.task_date_time)) },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            datePickerDialog.show()
                            timePickerDialog.show()
                        }) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = stringResource(id = R.string.task_select_date_time),
                                tint = AccentColor
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = LightGray,
                        focusedIndicatorColor = LightGray,
                        cursorColor = LightGray,
                        unfocusedLabelColor = LightGray,
                        focusedLabelColor = LightGray,
                        disabledIndicatorColor = LightGray
                    )
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botón Guardar Tarea
        Button(
            onClick = {
                viewModel.createNewTask(
                    title = title,
                    description = description,
                    imgPath = imgPath,
                    onSuccess = {
                        Toast.makeText(context, context.getString(R.string.task_created_success), Toast.LENGTH_SHORT).show()
                        onBack()
                    },
                    onFailure = {
                        Toast.makeText(context, context.getString(R.string.task_created_error), Toast.LENGTH_SHORT).show()
                    }
                )
            },
            enabled = title.isNotEmpty() && description.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(containerColor = AccentColor),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(55.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = stringResource(id = R.string.task_save),
                color = SecondAccent,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
