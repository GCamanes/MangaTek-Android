package com.groupany.mangatek.core.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.groupany.base.CustomFailure
import com.groupany.mangatek.core.extensions.trad
import com.groupany.ui.R
import com.groupany.ui.components.CustomButton
import com.groupany.ui.components.CustomSpacerSize
import com.groupany.ui.components.VerticalSpacer
import com.groupany.ui.constants.UIConstants

@Composable
fun CustomError(
    failure: CustomFailure,
    onRetry: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = UIConstants.PaddingMedium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.app_failure),
            contentDescription = failure.message,
            modifier = Modifier.size(250.dp)
        )
        VerticalSpacer(CustomSpacerSize.LARGE)
        Text(
            failure.trad(context),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier.weight(1f).padding(vertical = UIConstants.PaddingBig),
            contentAlignment = Alignment.BottomCenter
        ) {
            CustomButton(
                onClick = { onRetry() },
                label = "Retry"
            )
        }
    }
}