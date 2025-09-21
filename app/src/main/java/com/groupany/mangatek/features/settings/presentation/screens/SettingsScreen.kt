package com.groupany.mangatek.features.settings.presentation.screens

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.groupany.localization.LocaleHelper
import com.groupany.mangatek.core.navigation.NavHelper
import com.groupany.mangatek.features.settings.presentation.components.SettingsElement
import com.groupany.mangatek.features.settings.presentation.viewmodels.SettingsViewModel
import com.groupany.ui.components.ButtonTypes
import com.groupany.ui.components.CustomButton
import com.groupany.ui.components.LanguageButton
import com.groupany.ui.components.VerticalSpacer
import com.groupany.ui.constants.UIConstants
import com.groupany.localization.R as localeR

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
                title = { Text(
                    stringResource(localeR.string.settings),
                    style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onBackground))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
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
                .padding(innerPadding)
                .padding(
                    bottom = UIConstants.PaddingBig,
                    start = UIConstants.PaddingMedium,
                    top = UIConstants.PaddingMedium,
                    end = UIConstants.PaddingMedium
                )
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(UIConstants.CornerRound),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    SettingsElement(title = stringResource(localeR.string.version)) {
                        Text(
                            appVersion,
                            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground)
                        )
                    }

                    HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, MaterialTheme.colorScheme.surfaceVariant)

                    SettingsElement(title = stringResource(localeR.string.language)) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(UIConstants.PaddingMedium)
                        ) {
                            LocaleHelper.supportedLanguages.forEach { locale ->
                                LanguageButton(
                                    iconRes = LocaleHelper.getLocaleFlag(locale),
                                    value = locale,
                                    selectedValue = selectedLocale,
                                    language = locale.toString(),
                                    onClick = {
                                        LocaleHelper.setLocale(context, locale)
                                        activity?.recreate()
                                    }
                                )
                            }
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