package com.example.readmatetasks.ui.screens.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.readmatetasks.data.repository.AuthRepository
import com.example.readmatetasks.R
import com.example.readmatetasks.ui.theme.*
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.animateLottieCompositionAsState

/**
 * Pantalla de registro de usuario.
 *
 * @param onRegister Acción a ejecutar cuando el usuario se registre correctamente.
 * @param onBack Acción para volver a la pantalla anterior.
 * @param factory Fábrica para crear el [RegisterViewModel].
 */
@Composable
fun RegisterScreen(
    onRegister: () -> Unit,
    onBack: () -> Unit,
    factory: RegisterViewModelFactory = RegisterViewModelFactory(AuthRepository())
) {
    val registerViewModel: RegisterViewModel = viewModel(factory = factory)
    val registerState by registerViewModel.registerState.collectAsStateWithLifecycle()

    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var passConfirm by remember { mutableStateOf("") }
    var passMatch by remember { mutableStateOf(true) }
    var username by remember { mutableStateOf("") }
    var isPassVisible by remember { mutableStateOf(false)}

    // Inicia sesión si el registro es exitoso
    LaunchedEffect(registerState) {
        if (registerState is RegisterState.Success) {
            onRegister()
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
                text = stringResource(id = R.string.init_new),
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de nombre de usuario
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(text = stringResource(id = R.string.name)) },
                keyboardOptions = KeyboardOptions.Default,
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

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de contraseña
            OutlinedTextField(
                value = pass,
                onValueChange = {
                    pass = it
                    passMatch = pass == passConfirm
                },
                label = { Text(text = stringResource(id = R.string.pass)) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (isPassVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                ),
                trailingIcon = {
                    IconButton(onClick = { isPassVisible = !isPassVisible }) {
                        Icon(
                            imageVector = if (isPassVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (isPassVisible) stringResource(id = R.string.pass_hide) else stringResource(id = R.string.pass_show),
                            tint = AccentColor
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de confirmación de contraseña
            OutlinedTextField(
                value = passConfirm,
                onValueChange = {
                    passConfirm = it
                    passMatch = pass == passConfirm
                },
                label = { Text(text = stringResource(id = R.string.pass_confirmation)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (isPassVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = LightGray,
                    focusedIndicatorColor = LightGray,
                    cursorColor = LightGray,
                    unfocusedLabelColor = LightGray,
                    focusedLabelColor = LightGray,
                    disabledIndicatorColor = LightGray
                ),
                trailingIcon = {
                    IconButton(onClick = { isPassVisible = !isPassVisible }) {
                        Icon(
                            imageVector = if (isPassVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (isPassVisible) stringResource(id = R.string.pass_hide) else stringResource(id = R.string.pass_show),
                            tint = AccentColor
                        )
                    }
                }
            )

            if (!passMatch) {
                Text(
                    text = stringResource(id = R.string.pass_match),
                    color = OrangeError,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Botón de Creación de cuenta
            Button(
                onClick = { registerViewModel.registerUser(email, pass, username) },
                enabled = passMatch && email.isNotBlank() && pass.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = AccentColor),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(280.dp)
                    .height(55.dp),
                shape = RoundedCornerShape(15.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.create_account),
                    color = SecondAccent,
                    style = MaterialTheme.typography.labelLarge,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Mensaje y botón para volver a inicio de sesión
            Text(
                text = stringResource(id = R.string.ex_account),
                style = MaterialTheme.typography.bodyMedium,
                color = DarkGray
            )

            TextButton(onClick = onBack) {
                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = TextAccent
                )
            }

            // Muestra mensaje de error si ocurre un fallo en el registro
            when (registerState) {
                is RegisterState.Error -> {
                    val errorMessage = stringResource(id = (registerState as RegisterState.Error).messageKey)
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

        // Animación de carga mientras se procesa el registro
        AnimatedVisibility(
            visible = registerState is RegisterState.Loading,
            enter = fadeIn(initialAlpha = 0f), // fade
            exit = fadeOut(targetAlpha = 0f)  // unfade
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

