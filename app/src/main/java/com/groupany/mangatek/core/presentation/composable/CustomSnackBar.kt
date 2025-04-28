package com.groupany.mangatek.core.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.groupany.mangatek.R
import com.groupany.ui.constants.UIConstants

enum class SnackBarTypes {
    SUCCESS, FAILURE
}

@Composable
fun CustomSnackBar (data: SnackbarData) {
    val backgroundColor = if (data.visuals.actionLabel == SnackBarTypes.SUCCESS.name) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.secondary
    val contentColor = if (data.visuals.actionLabel == SnackBarTypes.SUCCESS.name) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSecondary
    val imageId = if (data.visuals.actionLabel == SnackBarTypes.SUCCESS.name) R.drawable.app_success
        else R.drawable.app_failure

    Box (
        modifier = Modifier.padding(UIConstants.PaddingMedium)
    ){
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(UIConstants.PaddingSmall))
                .background(backgroundColor)
                .padding(UIConstants.PaddingSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = data.visuals.actionLabel,
                modifier = Modifier.size(40.dp)
            )

            HorizontalSpacer(size = CustomSpacerSize.SMALL)

            Text(
                text = data.visuals.message,
                color = contentColor,
                style = MaterialTheme.typography.bodyMedium.copy(color = contentColor)
            )
        }
    }
}