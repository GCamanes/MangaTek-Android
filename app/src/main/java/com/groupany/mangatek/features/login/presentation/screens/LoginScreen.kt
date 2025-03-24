package com.groupany.mangatek.features.login.presentation.screens

import CustomTextField
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var userName by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        CustomTextField(
            label = "Username",
            initialValue = userName,
            onValueChange = { userName = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password TextField
        CustomTextField(
            label = "Password",
            initialValue = password,
            isPassword = true,
            onValueChange = { password = it }
        )

        Spacer(modifier = Modifier.height(60.dp))

        Box(
            contentAlignment = Alignment.Center,
        ) {
            Button(
                onClick = { navController.navigate("home") },
                modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)
            ) {
                Text("Login")
            }
        }
    }
}