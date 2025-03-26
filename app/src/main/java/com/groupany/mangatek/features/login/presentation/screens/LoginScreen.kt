package com.groupany.mangatek.features.login.presentation.screens

import CustomTextField
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseUser
import com.groupany.mangatek.core.navigation.Screen
import com.groupany.mangatek.core.states.GenericState
import com.groupany.mangatek.features.login.presentation.viewmodels.LoginViewModel

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        snapshotFlow { loginState }
            .collect { state ->
                if (state is GenericState.Success) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo("login") { inclusive = true }
                        launchSingleTop = true
                    }
                    viewModel.resetLoginState()
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            label = "Email",
            initialValue = email,
            onValueChange = viewModel::onUserNameChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password TextField
        CustomTextField(
            label = "Password",
            initialValue = password,
            isPassword = true,
            onValueChange = viewModel::onPasswordChange
        )

        Spacer(modifier = Modifier.height(60.dp))

        Box(
            contentAlignment = Alignment.Center,
        ) {
            Button(
                onClick = {
                    viewModel.loginUser(email, password)
                },
                modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)
            ) {
                Text("Login")
            }
        }

        when (loginState) {
            is GenericState.Idle -> Text("Enter your credentials")
            is GenericState.Loading -> CircularProgressIndicator()
            is GenericState.Success -> {
                val user = (loginState as GenericState.Success<FirebaseUser?>).value
                Text("Welcome ${user?.email ?: "User"}")
            }
            is GenericState.Error -> {
                Text("Error: ${(loginState as GenericState.Error).message}", color = Color.Red)
            }
        }
    }
}