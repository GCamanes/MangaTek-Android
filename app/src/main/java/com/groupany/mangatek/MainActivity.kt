package com.groupany.mangatek

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.groupany.mangatek.core.ui.theme.MangaTekTheme
import androidx.navigation.compose.rememberNavController
import com.groupany.mangatek.core.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MangaTekTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}

@HiltAndroidApp
class MangaTekApp : Application()