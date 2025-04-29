package com.groupany.mangatek.features.settings.presentation.screens

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.WindowInsets
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
import com.groupany.localization.LocaleHelper
import com.groupany.localization.R as localeR
import com.groupany.ui.components.ButtonTypes
import com.groupany.ui.components.CustomButton
import com.groupany.ui.components.VerticalSpacer
import com.groupany.mangatek.core.helpers.NavHelper
import com.groupany.ui.components.CustomSpacerSize
import com.groupany.ui.components.LanguageButton
import com.groupany.mangatek.features.settings.presentation.composables.SettingsElement
import com.groupany.mangatek.features.settings.presentation.viewmodels.SettingsViewModel
import com.groupany.ui.constants.UIConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController, viewModel: SettingsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val activity = context as? Activity // Needed to recreate activity
    var selectedLocale by remember { mutableStateOf(LocaleHelper.getCurrentLocale(context)) }
    val appVersion by viewModel.appVersion.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(localeR.string.settings)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        contentWindowInsets = WindowInsets(0.dp),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding).padding(
                    horizontal = UIConstants.PaddingMedium,
                    vertical = UIConstants.PaddingBig,
                )
        ) {
            Column(
                modifier = Modifier.weight(1f).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VerticalSpacer()

                SettingsElement(title = stringResource(localeR.string.version)) {
                    Text(
                        appVersion,
                        style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.primary)
                    )
                }

                VerticalSpacer(CustomSpacerSize.BIG)

                SettingsElement(title = stringResource(localeR.string.language)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(UIConstants.PaddingMedium)
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
            }

            VerticalSpacer()

            CustomButton(
                onClick = {
                    viewModel.logoutUser()
                    NavHelper.backToLogin(navController)
                },
                label = stringResource(localeR.string.logout),
                buttonType = ButtonTypes.SECONDARY
            )
        }
    }
}