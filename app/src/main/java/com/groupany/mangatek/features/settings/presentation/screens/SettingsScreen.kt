package com.groupany.mangatek.features.settings.presentation.screens

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.groupany.mangatek.R
import com.groupany.mangatek.core.helpers.LocaleHelper
import com.groupany.mangatek.features.settings.presentation.viewmodels.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController, viewModel: SettingsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val activity = context as? Activity // Needed to recreate activity
    var language by remember { mutableStateOf(LocaleHelper.getCurrentLocale(context)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Button(
                    onClick = { viewModel.logoutUser(navController) },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)
                ) {
                    Text(stringResource(R.string.logout))
                }
            }

            Text(text = "Current Language: $language")

            Button(onClick = {
                val newLanguage = if (language == "en") "fr" else "en"
                language = newLanguage
                LocaleHelper.setLocale(context, newLanguage)
                activity?.recreate() // Restart activity to apply changes
            }) {
                Text(text = "Switch Language")
            }
        }
    }
}