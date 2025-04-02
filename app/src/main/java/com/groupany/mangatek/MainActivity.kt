package com.groupany.mangatek

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowInsetsControllerCompat
import com.groupany.mangatek.core.theme.MangaTekTheme
import androidx.navigation.compose.rememberNavController
import com.groupany.mangatek.core.helpers.LocaleHelper
import com.groupany.mangatek.core.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Set dark theme
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        // Make the status bar text/icons white
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = false // Set white status bar
        }
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