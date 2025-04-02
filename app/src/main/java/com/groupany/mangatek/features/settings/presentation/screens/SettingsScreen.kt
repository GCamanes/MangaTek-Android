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
import androidx.compose.ui.text.style.TextDecoration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.groupany.mangatek.R
import com.groupany.mangatek.core.helpers.LocaleHelper
import com.groupany.mangatek.core.presentation.composable.ButtonTypes
import com.groupany.mangatek.core.presentation.composable.CustomButton
import com.groupany.mangatek.core.presentation.composable.VerticalSpacer
import com.groupany.mangatek.core.constants.Dimension
import com.groupany.mangatek.core.presentation.composable.CustomSpacerSize
import com.groupany.mangatek.features.settings.presentation.composables.LanguageButton
import com.groupany.mangatek.features.settings.presentation.viewmodels.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController, viewModel: SettingsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val activity = context as? Activity // Needed to recreate activity
    var selectedLocale by remember { mutableStateOf(LocaleHelper.getCurrentLocale(context)) }

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
                .padding(innerPadding).padding(Dimension.PaddingMedium),
        ) {
            Column(
                modifier = Modifier.weight(1f).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VerticalSpacer()

                Text(
                    stringResource(R.string.version),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        textDecoration = TextDecoration.Underline
                    )
                )

                VerticalSpacer()

                Text(
                    viewModel.appVersion,
                    style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.primary)
                )

                VerticalSpacer(CustomSpacerSize.BIG)

                Text(
                    stringResource(R.string.language),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        textDecoration = TextDecoration.Underline
                    )
                )

                VerticalSpacer()

                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimension.PaddingMedium)
                ) {
                    LocaleHelper.supportedLanguages.forEach { locale ->
                        LanguageButton(
                            iconRes = LocaleHelper.getLocaleFlag(locale),
                            value = locale,
                            selectedValue = selectedLocale,
                            onClick = {
                                LocaleHelper.setLocale(context, locale)
                                activity?.recreate()
                            }
                        )
                    }
                }
            }

            VerticalSpacer()

            CustomButton(
                onClick = { viewModel.logoutUser(navController) },
                label = stringResource(R.string.logout),
                buttonType = ButtonTypes.SECONDARY
            )

            VerticalSpacer()
        }
    }
}