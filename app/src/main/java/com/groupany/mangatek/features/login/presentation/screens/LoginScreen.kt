package com.groupany.mangatek.features.login.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.groupany.mangatek.core.navigation.Screen
import com.groupany.mangatek.core.states.GenericState
import com.groupany.mangatek.features.login.domain.entities.UserEntity
import com.groupany.mangatek.features.login.presentation.composables.CustomTextField
import com.groupany.mangatek.features.login.presentation.viewmodels.LoginViewModel
import com.groupany.mangatek.R

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val validationSTate by viewModel.validationState.collectAsState()
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    val currentUserState by viewModel.currentUserState.collectAsStateWithLifecycle()

    // Retrieve user bloc
    LaunchedEffect(Unit) { viewModel.getCurrentUser() }
    LaunchedEffect(Unit) {
        snapshotFlow { currentUserState }
            .collect { state ->
                if (state is GenericState.Success && state.value != null) {
                    gotToHome(navController)
                }
            }
    }
    // Login user bloc
    LaunchedEffect(Unit) {
        snapshotFlow { loginState }
            .collect { state ->
                if (state is GenericState.Success) {
                    gotToHome(navController)
                }
            }
    }

    Scaffold { innerPadding -> Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding).padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            label = stringResource(R.string.email),
            initialValue = email,
            enabled = !loginState.isLoading() && !loginState.isSuccess(),
            onValueChange = viewModel::onEmailChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            label = stringResource(R.string.password),
            initialValue = password,
            isPassword = true,
            enabled = !loginState.isLoading() && !loginState.isSuccess(),
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
                enabled = validationSTate.isValid()
                        && !loginState.isLoading()
                        && !loginState.isSuccess(),
                modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)
            ) {
                Text(stringResource(R.string.login))
            }
        }

        when (loginState) {
            is GenericState.Idle -> Text("Enter your credentials")
            is GenericState.Loading -> CircularProgressIndicator()
            is GenericState.Success -> {
                val user = (loginState as GenericState.Success<UserEntity>).value
                Text("Welcome ${user.email}")
            }
            is GenericState.Error -> {
                Text("Error: ${(loginState as GenericState.Error).message}", color = Color.Red)
            }
        }
    }
        }
}

fun gotToHome(navController: NavHostController) {
    navController.navigate(Screen.Home.route) {
        popUpTo(Screen.Login.route) { inclusive = true }
        launchSingleTop = true
    }
}