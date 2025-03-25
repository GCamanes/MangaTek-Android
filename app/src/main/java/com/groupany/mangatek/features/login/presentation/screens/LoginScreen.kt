package com.groupany.mangatek.features.login.presentation.screens

import CustomTextField
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.groupany.mangatek.core.navigation.Screen
import com.groupany.mangatek.features.login.presentation.viewmodels.LoginViewModel

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val userName by viewModel.userName.collectAsState()
    val password by viewModel.password.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            label = "Username",
            initialValue = userName,
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
                onClick = { navController.navigate(Screen.Home.route) },
                modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)
            ) {
                Text("Login")
            }
        }
    }
}