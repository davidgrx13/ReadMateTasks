package com.example.readmatetasks.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.readmatetasks.ui.theme.*
import androidx.compose.material3.MaterialTheme

/**
 * Un botón común.
 *
 * @param textResId El recurso de cadena que representa el texto del botón.
 * @param onClick Acción que se ejecuta cuando se presiona el botón.
 * @param enabled Indica si el botón está habilitado o deshabilitado.
 */
@Composable
fun CommonButton(
    textResId: Int,
    onClick: () -> Unit,
    enabled: Boolean
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = AccentColor),
        modifier = Modifier
            .width(280.dp)
            .height(55.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(
            text = stringResource(id = textResId),
            color = SecondAccent,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

/**
 * Un botón utilizado para acciones relacionadas con tareas.
 *
 * @param textResId El recurso de cadena que representa el texto del botón.
 * @param onClick Acción que se ejecuta cuando se presiona el botón.
 */
@Composable
fun TaskButton(
    textResId: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = AccentColor),
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(
            text = stringResource(id = textResId),
            color = SecondAccent,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
