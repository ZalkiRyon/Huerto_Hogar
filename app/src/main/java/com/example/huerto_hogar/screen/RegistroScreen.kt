package com.example.huerto_hogar.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.huerto_hogar.model.RegistrationResult
import com.example.huerto_hogar.ui.theme.components.InputField
import com.example.huerto_hogar.ui.theme.components.animations.bounceInEffect
import com.example.huerto_hogar.ui.theme.components.animations.pressClickEffect
import com.example.huerto_hogar.viewmodel.RegisterUserViewModel

@Composable
fun RegistroScreen(navController: NavController, viewModel: RegisterUserViewModel) {

    val formState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    var showSuccessDialog by remember { mutableStateOf(false) }
    var finalResult by remember { mutableStateOf<RegistrationResult?>(null) }
    val context = LocalContext.current

    val resultEvent = formState.registrationResultEvent
    LaunchedEffect(resultEvent) {
        if (resultEvent != null) {

            finalResult = resultEvent

            when (resultEvent) {
                RegistrationResult.SUCCESS -> {
                    showSuccessDialog = true
                }

                RegistrationResult.EMAIL_ALREADY_EXISTS -> {
                    Toast.makeText(
                        context,
                        "Error: El correo electrónico ya está registrado.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                RegistrationResult.ERROR -> {
                    Toast.makeText(
                        context,
                        "Error al registrar. Verifique sus datos o intente más tarde.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            viewModel.clearRegistrationResultEvent()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(vertical = 32.dp, horizontal = 16.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Crear cuenta",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .bounceInEffect(delay = 0)
            )

            InputField(
                value = formState.name,
                onValueChange = { viewModel.onChangeName(it) },
                label = "Nombre",
                placeholder = "Entre 4 y 20 caracteres, solo letras",
                error = formState.errors.nameError,
                modifier = Modifier.bounceInEffect(delay = 50)
            )

            InputField(
                value = formState.lastname,
                onValueChange = { viewModel.onChangeLastname(it) },
                label = "Apellido",
                placeholder = "Entre 4 y 20 caracteres, solo letras",
                error = formState.errors.lastnameError,
                modifier = Modifier.bounceInEffect(delay = 100)
            )

            InputField(
                value = formState.email,
                onValueChange = { viewModel.onChangeEmail(it) },
                label = "Correo electrónico",
                placeholder = "Solo @duocuc.cl o @profesor.duoc.cl",
                error = formState.errors.emailError,
                modifier = Modifier.bounceInEffect(delay = 150)
            )

            InputField(
                value = formState.password,
                onValueChange = { viewModel.onChangePassword(it) },
                label = "Contraseña",
                error = formState.errors.passwordError,
                modifier = Modifier.bounceInEffect(delay = 200),
                isPassword = true
            )

            InputField(
                value = formState.confirmPassword,
                onValueChange = { viewModel.onChangeConfirmPassword(it) },
                label = "Confirmar contraseña",
                error = formState.errors.confirmPasswordError,
                modifier = Modifier.bounceInEffect(delay = 250),
                isPassword = true
            )

            InputField(
                value = formState.address,
                onValueChange = { viewModel.onChangeAddress(it) },
                label = "Dirección",
                placeholder = "Entre 5 y 40 caracteres",
                error = formState.errors.addressError,
                modifier = Modifier.bounceInEffect(delay = 300)
            )
            InputField(
                value = formState.phone,
                onValueChange = { viewModel.onChangePhone(it) },
                label = "Teléfono (opcional)",
                placeholder = "Solo números, entre 8 y 9 dígitos",
                error = formState.errors.phoneError,
                modifier = Modifier.bounceInEffect(delay = 350)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.onClickRegister() },
                modifier = Modifier
                    .fillMaxWidth()
                    .bounceInEffect(delay = 400)
                    .height(56.dp)
                    .pressClickEffect(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "REGISTRAR",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            if (showSuccessDialog) {
                val user = formState.registeredUser
                AlertDialog(
                    onDismissRequest = { },
                    title = { Text(text = "¡Registro Exitoso!") },
                    //text = { Text("Tu cuenta ha sido creada con éxito. Serás redirigido al inicio de sesión para continuar.") },
                    text = {
                        Column {
                            Text("Tu cuenta ha sido creada con éxito. Aquí están tus detalles:")
                            Spacer(modifier = Modifier.height(8.dp))

                            // Mostrando los detalles del usuario
                            Text("Nombre: ${user?.name} ${user?.lastname}")
                            Text("Email: ${user?.email}")
                            Text("Rol Asignado: ${user?.role ?: "CLIENTE"}")
                            Text("Dirección: ${user?.address}")
                            Text("Teléfono: ${user?.phone ?: "No Registrado"}")

                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Serás redirigido al inicio de sesión para continuar.")
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showSuccessDialog = false

                                if (finalResult == RegistrationResult.SUCCESS) {
                                    navController.navigate("login_screen") {
                                        popUpTo(navController.graph.id) { inclusive = true }
                                    }
                                }
                                viewModel.resetUiState()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text("Ir a Iniciar Sesión")
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(20.dp)
                )
            }
        }
    }

}
