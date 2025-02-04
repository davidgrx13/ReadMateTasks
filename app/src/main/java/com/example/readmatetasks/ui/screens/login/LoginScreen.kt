package com.example.readmatetasks.ui.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.readmatetasks.data.repository.AuthRepository
import com.example.readmatetasks.R
import com.example.readmatetasks.ui.theme.*
import com.airbnb.lottie.compose.*
import com.example.readmatetasks.ui.components.CommonButton

/**
 * Pantalla de inicio de sesión.
 *
 * @param onLogin Acción a ejecutar cuando el usuario inicie sesión correctamente.
 * @param onRegister Acción para navegar a la pantalla de registro.
 * @param factory Fábrica para crear el [LoginViewModel].
 */
@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    factory: LoginViewModelFactory = LoginViewModelFactory(AuthRepository())
) {
    val loginViewModel: LoginViewModel = viewModel(factory = factory)
    val loginState by loginViewModel.loginState.collectAsStateWithLifecycle()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Si el estado es éxito, navegar a la pantalla principal
    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            onLogin()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.readmate_icon),
                contentDescription = stringResource(id = R.string.app_logo),
                modifier = Modifier
                    .height(60.dp)
                    .width(270.dp)
            )

            Spacer(modifier = Modifier.height(60.dp))

            // Mensaje de bienvenida
            Text(
                text = stringResource(id = R.string.init_old),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light,
                    color = AccentColor,
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Campo de email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = stringResource(id = R.string.email)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = stringResource(id = R.string.pass)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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

            Spacer(modifier = Modifier.height(30.dp))

            // Botón de inicio de sesión
            CommonButton(
                textResId = R.string.sign_in,
                onClick = { loginViewModel.loginUser(email, password) },
                enabled = email.isNotBlank() && password.isNotBlank()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mensaje y botón para registro
            Text(
                text = stringResource(id = R.string.dont_account),
                style = MaterialTheme.typography.bodyMedium,
                color = DarkGray
            )
            TextButton(onClick = onRegister) {
                Text(
                    text = stringResource(id = R.string.not_account),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = TextAccent
                )
            }

            // Muestra mensaje de error si ocurre un fallo en el inicio de sesión
            when (loginState) {
                is LoginState.Error -> {
                    val errorMessage = stringResource(id = (loginState as LoginState.Error).messageKey)
                    Text(
                        text = errorMessage,
                        color = OrangeError,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                else -> Unit
            }
        }

        // Animación de carga mientras se inicia sesión
        AnimatedVisibility(
            visible = loginState is LoginState.Loading,
            enter = fadeIn(initialAlpha = 0f),
            exit = fadeOut(targetAlpha = 0f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundColor.copy(alpha = 0.8f)) // Fondo semitransparente
                    .clickable(enabled = false) {}, // Bloquea la interacción
                contentAlignment = Alignment.Center
            ) {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
                val progress by animateLottieCompositionAsState(composition)

                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier.size(250.dp)
                )
            }
        }
    }
}
