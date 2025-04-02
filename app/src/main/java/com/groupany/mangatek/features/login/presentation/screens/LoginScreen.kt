package com.groupany.mangatek.features.login.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.groupany.mangatek.core.navigation.Screen
import com.groupany.mangatek.core.states.GenericState
import com.groupany.mangatek.core.presentation.composable.CustomTextField
import com.groupany.mangatek.features.login.presentation.viewmodels.LoginViewModel
import com.groupany.mangatek.R
import com.groupany.mangatek.core.presentation.composable.CustomButton
import com.groupany.mangatek.core.presentation.composable.CustomSpacerSize
import com.groupany.mangatek.core.presentation.composable.VerticalSpacer
import com.groupany.mangatek.core.constants.Dimension

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
    var isFormVisible by remember { mutableStateOf(false) }
    // Focus handle
    val localFocusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    // Retrieve user bloc
    LaunchedEffect(Unit) { viewModel.getCurrentUser() }
    LaunchedEffect(Unit) {
        snapshotFlow { currentUserState }
            .collect { state ->
                if (state.valueOrNull != null) {
                    gotToHome(navController)
                } else if (state.isSuccess()) {
                    isFormVisible = true
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
                .verticalScroll(scrollState)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        localFocusManager.clearFocus()
                    })
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VerticalSpacer(CustomSpacerSize.BIG)

            Image(
                painter = painterResource(id = R.drawable.mangatek_logo),
                contentDescription = "app logo",
                modifier = Modifier.size(screenWidth / 3)
            )

            Column(
                modifier = Modifier.weight(1f),
            ) {
                if (!isFormVisible) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
                    }
                }

                AnimatedVisibility(
                    visible = isFormVisible,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000))
                            + slideInVertically(initialOffsetY = { it }, animationSpec = tween(durationMillis = 1000)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 300))
                            + slideOutVertically(targetOffsetY = { it }, animationSpec = tween(durationMillis = 300)),
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        VerticalSpacer(CustomSpacerSize.BIG)

                        CustomTextField(
                            label = stringResource(R.string.email),
                            initialValue = email,
                            enabled = !loginState.isLoading() && !loginState.isSuccess(),
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                            onValueChange = viewModel::onEmailChange
                        )

                        VerticalSpacer()

                        CustomTextField(
                            label = stringResource(R.string.password),
                            initialValue = password,
                            isPassword = true,
                            enabled = !loginState.isLoading() && !loginState.isSuccess(),
                            onDoneAction = {
                                if (validationSTate.isValid()) {
                                    viewModel.loginUser(email, password)
                                }
                            },
                            onValueChange = viewModel::onPasswordChange
                        )

                        VerticalSpacer(CustomSpacerSize.BIG)

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

            Text(viewModel.appVersion)
        }
    }
}

fun gotToHome(navController: NavHostController) {
    navController.navigate(Screen.Home.route) {
        popUpTo(Screen.Login.route) { inclusive = true }
        launchSingleTop = true
    }
}