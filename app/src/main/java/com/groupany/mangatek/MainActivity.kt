package com.groupany.mangatek

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

import androidx.navigation.compose.rememberNavController
import com.groupany.localization.LocaleHelper
import com.groupany.mangatek.navigation.AppNavHost
import com.groupany.ui.theme.MangaTekTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.Transparent.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(Color.Transparent.toArgb()),
        )
        setContent {
            MangaTekTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val savedLanguage = LocaleHelper.getSavedLanguage(newBase)

        val initialLanguage = if (savedLanguage.isEmpty()) {
            LocaleHelper.getDefaultSystemLanguage()
        } else {
            savedLanguage
        }

        super.attachBaseContext(LocaleHelper.setLocale(newBase, initialLanguage))
    }
}

@HiltAndroidApp
class MangaTekApp : Application()