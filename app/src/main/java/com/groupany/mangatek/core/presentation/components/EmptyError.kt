package com.groupany.mangatek.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.groupany.mangatek.R
import com.groupany.ui.components.VerticalSpacer
import com.groupany.ui.constants.UIConstants
import com.groupany.ui.R as uiR

@Composable
fun EmptyError() {
    Column(
        modifier = Modifier.fillMaxSize().padding(UIConstants.PaddingMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = uiR.drawable.app_failure),
            contentDescription = stringResource(R.string.error_empty),
            modifier = Modifier.size(200.dp) // or whatever size you want
        )
        VerticalSpacer()
        Text(
            stringResource(R.string.error_empty),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(2f))
    }
}