package com.groupany.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.groupany.ui.constants.UIConstants

enum class CustomSpacerSize(val height: Dp) {
    EXTRA_SMALL(UIConstants.PaddingExtraSmall),
    SMALL(UIConstants.PaddingSmall),
    MEDIUM(UIConstants.PaddingMedium),
    LARGE(UIConstants.PaddingLarge),
    BIG(UIConstants.PaddingBig)
}

@Composable
fun VerticalSpacer(size: CustomSpacerSize = CustomSpacerSize.MEDIUM) {
    Spacer(modifier = Modifier.height(size.height))
}

@Composable
fun HorizontalSpacer(size: CustomSpacerSize = CustomSpacerSize.MEDIUM) {
    Spacer(modifier = Modifier.width(size.height))
}