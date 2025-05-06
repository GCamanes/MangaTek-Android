package com.groupany.mangatek.features.auth.presentation.screens

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.groupany.localization.extensions.trad
import com.groupany.ui.components.CustomTextField
import com.groupany.mangatek.features.auth.presentation.viewmodels.LoginViewModel
import com.groupany.localization.R as localeR
import com.groupany.ui.components.CustomButton
import com.groupany.ui.components.CustomSpacerSize
import com.groupany.ui.components.VerticalSpacer
import com.groupany.mangatek.core.helpers.NavHelper
import com.groupany.ui.components.CustomSnackBar
import com.groupany.mangatek.core.presentation.components.MangaTekTitle
import com.groupany.ui.components.SnackBarTypes
import com.groupany.ui.snackbar.SnackBarHandler
import com.groupany.ui.snackbar.SnackBarManager
import com.groupany.ui.constants.UIConstants
import com.groupany.ui.R as uiR

@Composable
fun LoginScreen(
    navController: NavHostController,
    autoAuth: Boolean,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    // Form values
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val validationSTate by viewModel.validationState.collectAsState()
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    val currentUserState by viewModel.currentUserState.collectAsStateWithLifecycle()
    var isFormVisible by remember { mutableStateOf(false) }
    // UI values
    val screenWidth = with(LocalDensity.current) {
        LocalWindowInfo.current.containerSize.width.toDp()
    }
    // Focus handle
    val localFocusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    val appVersion by viewModel.appVersion.collectAsState()

    // Retrieve user bloc
    LaunchedEffect(Unit) {
        if (autoAuth) {
            viewModel.getCurrentUser()
        } else {
            isFormVisible = true
        }
    }
    LaunchedEffect(Unit) {
        snapshotFlow { currentUserState }
            .collect { state ->
                if (state.isSuccess()) {
                    NavHelper.gotToHome(navController)
                } else if (state.isFailure()) {
                    isFormVisible = true
                }
            }
    }
    // Login user bloc
    LaunchedEffect(Unit) {
        snapshotFlow { loginState }
            .collect { state ->
                if (state.isSuccess()) {
                    NavHelper.gotToHome(navController)
                } else if (state.isFailure()) {
                    SnackBarManager.showSnackBar(
                        state.failureOrNull!!.trad(context),
                        SnackBarTypes.FAILURE
                    )
                }
            }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(SnackBarManager.snackBarHostState) { data -> CustomSnackBar(data) }
        }
    ) {
        innerPadding -> Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding).padding(UIConstants.PaddingMedium)
                .verticalScroll(scrollState)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        localFocusManager.clearFocus()
                    })
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SnackBarHandler()

            VerticalSpacer(CustomSpacerSize.BIG)

            Image(
                painter = painterResource(id = uiR.drawable.mangatek_logo),
                contentDescription = "app logo",
                modifier = Modifier.size(screenWidth / 3)
            )
            MangaTekTitle()

            Column(modifier = Modifier.weight(1f)) {
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
                            label = stringResource(localeR.string.email),
                            initialValue = email,
                            enabled = !loginState.isLoading() && !loginState.isSuccess(),
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                            onValueChange = viewModel::onEmailChange
                        )

                        VerticalSpacer()

                        CustomTextField(
                            label = stringResource(localeR.string.password),
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
                            onClick = { viewModel.loginUser(email, password) },
                            isLoading = loginState.isLoading() || loginState.isSuccess(),
                            enabled = validationSTate.isValid(),
                            label = stringResource(localeR.string.login)
                        )
                    }
                }
            }

            Text(appVersion)
        }
    }
}