package com.groupany.mangatek.core.snackbar

import androidx.compose.material3.SnackbarHostState
import com.groupany.ui.components.SnackBarTypes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackBarMessage(
    val message: String,
    val type: SnackBarTypes,
)

object SnackBarManager {
    val snackBarHostState = SnackbarHostState()

    private val snackBarChannel = Channel<SnackBarMessage>(Channel.BUFFERED)
    val snackBarFlow = snackBarChannel.receiveAsFlow()

    suspend fun showSnackBar( message: String, type: SnackBarTypes) {
        snackBarChannel.send(SnackBarMessage(message, type))
    }
}