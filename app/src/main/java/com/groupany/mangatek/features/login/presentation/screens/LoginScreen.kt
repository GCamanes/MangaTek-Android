package com.groupany.mangatek.features.login.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.groupany.mangatek.core.navigation.Screen
import com.groupany.mangatek.core.states.GenericState
import com.groupany.mangatek.features.login.presentation.composables.CustomTextField
import com.groupany.mangatek.features.login.presentation.viewmodels.LoginViewModel
import com.groupany.mangatek.R
import com.groupany.mangatek.core.presentation.composable.CustomButton
import com.groupany.mangatek.core.presentation.composable.CustomSpacerSize
import com.groupany.mangatek.core.presentation.composable.VerticalSpacer
import com.groupany.mangatek.core.ui.Dimension

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    // Form values
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val validationSTate by viewModel.validationState.collectAsState()
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    val currentUserState by viewModel.currentUserState.collectAsStateWithLifecycle()
    // UI values
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    // Focus handle
    val localFocusManager = LocalFocusManager.current

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

    Scaffold {
        innerPadding -> Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding).padding(Dimension.PaddingMedium)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        localFocusManager.clearFocus()
                    })
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.mangatek_logo),
                contentDescription = "app logo",
                modifier = Modifier.size(screenWidth / 3)
            )

            VerticalSpacer(CustomSpacerSize.LARGE)

            CustomTextField(
                label = stringResource(R.string.email),
                initialValue = email,
                enabled = !loginState.isLoading() && !loginState.isSuccess(),
                onValueChange = viewModel::onEmailChange
            )

            VerticalSpacer()

            CustomTextField(
                label = stringResource(R.string.password),
                initialValue = password,
                isPassword = true,
                enabled = !loginState.isLoading() && !loginState.isSuccess(),
                onValueChange = viewModel::onPasswordChange
            )

            VerticalSpacer(CustomSpacerSize.BIG)

            Box(
                contentAlignment = Alignment.Center,
            ) {
                CustomButton(
                    onClick = {
                        viewModel.loginUser(email, password)
                    },
                    isLoading = loginState.isLoading()
                            || loginState.isSuccess(),
                    enabled = validationSTate.isValid(),
                    label = stringResource(R.string.login)
                )
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