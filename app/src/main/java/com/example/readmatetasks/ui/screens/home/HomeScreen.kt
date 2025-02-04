package com.example.readmatetasks.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.readmatetasks.R
import com.example.readmatetasks.ui.session.AuthSessionViewModel
import com.example.readmatetasks.ui.theme.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.readmatetasks.ui.components.TaskButton

/**
 * Pantalla principal del usuario, donde puede ver su perfil y acceder a las opciones de gestión de tareas.
 *
 * @param onNavigateToCreateTask Acción para navegar a la pantalla de creación de tareas.
 * @param onNavigateToListTasks Acción para navegar a la lista de tareas.
 * @param homeViewModel ViewModel para la lógica de la pantalla principal.
 * @param authSessionViewModel ViewModel para gestionar la sesión del usuario.
 * @param logoutUser Acción para cerrar sesión.
 */
@Composable
fun HomeScreen(
    onNavigateToCreateTask: () -> Unit,
    onNavigateToListTasks: () -> Unit,
    homeViewModel: HomeViewModel,
    authSessionViewModel: AuthSessionViewModel,
    logoutUser: () -> Unit
) {
    val user by authSessionViewModel.username.collectAsState()

    // Cargar datos del usuario al iniciar la pantalla
    LaunchedEffect(Unit) {
        authSessionViewModel.fetchCurrentUser()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(WindowInsets.systemBars.asPaddingValues()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(25.dp))

        // Tarjeta con información del usuario
        UserCard(
            name = user ?: stringResource(id = R.string.user_unknown),
            logoutUser = {
                homeViewModel.logout()
                logoutUser()
            }
        )

        // Menú de opciones de navegación
        OptionMenu(
            onNavigateToCreateTask = onNavigateToCreateTask,
            onNavigateToListTasks = onNavigateToListTasks
        )
    }
}

/**
 * Tarjeta de usuario que muestra el nombre y un botón para cerrar sesión.
 *
 * @param name Nombre del usuario.
 * @param logoutUser Acción para cerrar sesión.
 */
@Composable
fun UserCard(
    name: String,
    logoutUser: () -> Unit
) {
    Text(
        text = stringResource(id = R.string.main_menu),
        style = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.Black,
            color = MainTitleColor,
            fontSize = 38.sp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp),
        textAlign = TextAlign.Left
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = stringResource(id = R.string.user_pic),
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = MainTitleColor
                    )
                )
            }
            Button(
                onClick = logoutUser,
                colors = ButtonDefaults.buttonColors(containerColor = AccentColor)
            ) {
                Text(text = stringResource(id = R.string.logout), color = SecondAccent)
            }
        }
    }
}

/**
 * Menú de opciones con botones para crear una nueva tarea o ver la lista de tareas.
 *
 * @param onNavigateToCreateTask Acción para navegar a la pantalla de creación de tareas.
 * @param onNavigateToListTasks Acción para navegar a la lista de tareas.
 */
@Composable
fun OptionMenu(
    onNavigateToCreateTask: () -> Unit,
    onNavigateToListTasks: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        TaskButton(
            textResId = R.string.task_create,
            onClick = onNavigateToCreateTask
        )

        Spacer(modifier = Modifier.height(12.dp))

        TaskButton(
            textResId = R.string.task_list,
            onClick = onNavigateToListTasks
        )
    }
}
