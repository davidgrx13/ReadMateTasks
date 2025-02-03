package com.example.readmatetasks.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.readmatetasks.data.repository.AuthRepository
import com.example.readmatetasks.data.repository.TaskRepository
import com.example.readmatetasks.ui.screens.home.*
import com.example.readmatetasks.ui.screens.login.LoginScreen
import com.example.readmatetasks.ui.screens.register.RegisterScreen
import com.example.readmatetasks.ui.screens.tasks.*
import com.example.readmatetasks.ui.session.*

/**
 * Configura la navegación principal de la aplicación.
 *
 * @param navController Controlador de navegación de Jetpack Compose.
 */
@Composable
fun MainNavHost(navController: NavHostController) {
    val authSessionViewModel: AuthSessionViewModel = viewModel(
        factory = AuthSessionViewModelFactory(AuthRepository())
    )

    // Cargar los datos del usuario al iniciar el componente
    LaunchedEffect(Unit) {
        authSessionViewModel.fetchCurrentUser()
    }

    val userEmail by authSessionViewModel.userEmail.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val taskRepository = remember {
        TaskRepository(
            context = context,
            firestore = FirebaseFirestore.getInstance()
        )
    }

    // Configuración de la navegación
    NavHost(
        navController = navController,
        startDestination = if (userEmail != null) Routes.Home.route else Routes.Login.route
    ) {
        composable(Routes.Login.route) {
            LoginScreen(
                onLogin = { navController.navigate(Routes.Home.route) },
                onRegister = { navController.navigate(Routes.Register.route) }
            )
        }

        composable(Routes.Register.route) {
            RegisterScreen(
                onRegister = { navController.navigate(Routes.Home.route) },
                onBack = { navController.navigateUp() }
            )
        }

        composable(Routes.Home.route) {
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModelFactory(authSessionViewModel)
            )

            HomeScreen(
                onNavigateToCreateTask = { navController.navigate(Routes.CrearTarea.route) },
                onNavigateToListTasks = { navController.navigate(Routes.ListadoTareas.route) },
                homeViewModel = homeViewModel,
                logoutUser = {
                    authSessionViewModel.logout()
                    navController.navigate(Routes.Login.route) {
                        popUpTo(Routes.Home.route) { inclusive = true }
                    }
                },
                authSessionViewModel = authSessionViewModel
            )
        }

        composable(Routes.ListadoTareas.route) {
            val taskListViewModel: TaskListViewModel = viewModel(
                factory = TaskListViewModelFactory(
                    taskRepository = taskRepository,
                    authRepository = AuthRepository(
                        firebaseAuth = FirebaseAuth.getInstance(),
                        firestore = FirebaseFirestore.getInstance()
                    )
                )
            )

            TaskList(
                viewModel = taskListViewModel,
                onBack = { navController.navigateUp() }
            )
        }

        composable(Routes.CrearTarea.route) {
            val newTaskViewModel: NewTaskViewModel = viewModel(
                factory = NewTaskViewModelFactory(
                    TaskRepository(
                        context = LocalContext.current,
                        firestore = FirebaseFirestore.getInstance()
                    ),
                    AuthRepository(
                        firebaseAuth = FirebaseAuth.getInstance(),
                        firestore = FirebaseFirestore.getInstance()
                    )
                )
            )

            NewTask(
                viewModel = newTaskViewModel,
                onBack = { navController.navigateUp() }
            )
        }
    }
}
