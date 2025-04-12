package com.groupany.mangatek.features.home.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.groupany.mangatek.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.groupany.mangatek.core.constants.AppDimension
import com.groupany.mangatek.core.helpers.NavHelper
import com.groupany.mangatek.features.home.presentation.composables.MangaCard
import com.groupany.mangatek.features.home.presentation.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    IconButton(onClick = { NavHelper.gotToSettings(navController) }) {
                        Icon(
                            Icons.Outlined.Settings, // Use your own icon
                            contentDescription = stringResource(R.string.settings),
                            modifier = Modifier.size(30.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
            )
        },
        contentWindowInsets = WindowInsets(0.dp),
    ) { paddingValues ->
            when {
                uiState.isLoading -> CircularProgressIndicator()
                uiState.error != null -> Text("Error: ${uiState.error}")
                else -> LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(
                        top = AppDimension.PaddingMedium,
                        start = AppDimension.PaddingMedium,
                        end = AppDimension.PaddingMedium,
                        bottom = AppDimension.PaddingBig,
                    ),
                    verticalArrangement = Arrangement.spacedBy(AppDimension.PaddingMedium)
                ) {
                    items(uiState.mangaList) { manga -> MangaCard(manga) }
                }
            }
    }
}