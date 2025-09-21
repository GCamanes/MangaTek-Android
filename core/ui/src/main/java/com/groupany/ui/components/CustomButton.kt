package com.groupany.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.groupany.ui.constants.UIConstants

enum class ButtonTypes {
    PRIMARY, SECONDARY
}

@Composable
fun CustomButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    label: String,
    buttonType: ButtonTypes = ButtonTypes.PRIMARY
) {
    val colors = when (buttonType) {
        ButtonTypes.PRIMARY -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
        ButtonTypes.SECONDARY -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
    }

    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        colors = colors,
        shape = RoundedCornerShape(UIConstants.CornerRound),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = UIConstants.ButtonHeight, max = UIConstants.ButtonHeight)
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = colors.containerColor)
        } else {
            Text(
                label,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 25.sp,
                    color = colors.contentColor
                )
            )
        }
    }
}