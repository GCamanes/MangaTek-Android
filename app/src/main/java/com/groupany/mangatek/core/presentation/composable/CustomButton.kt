package com.groupany.mangatek.core.presentation.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.groupany.mangatek.core.ui.Dimension

@Composable
fun CustomButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    label: String,
) {
    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = Dimension.ButtonHeight, max = Dimension.ButtonHeight)
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
        } else {
            Text(
                label,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}