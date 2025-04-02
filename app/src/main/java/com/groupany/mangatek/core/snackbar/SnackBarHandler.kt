package com.groupany.mangatek.core.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun SnackBarHandler() {
    val snackBarHostState = remember { SnackBarManager.snackBarHostState }
    val coroutineScope = rememberCoroutineScope()
    var currentSnackBarJob by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(Unit) {
        SnackBarManager.snackBarFlow.collect { data ->
            currentSnackBarJob?.cancel()
            currentSnackBarJob = coroutineScope.launch {
                snackBarHostState.showSnackbar(
                    data.message,
                    data.type.toString(),
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
}