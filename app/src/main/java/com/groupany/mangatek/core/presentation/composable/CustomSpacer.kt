package com.groupany.mangatek.core.presentation.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.groupany.mangatek.core.constants.AppDimension

enum class CustomSpacerSize(val height: Dp) {
    SMALL(AppDimension.PaddingSmall),
    MEDIUM(AppDimension.PaddingMedium),
    LARGE(AppDimension.PaddingLarge),
    BIG(AppDimension.PaddingBig)
}

@Composable
fun VerticalSpacer(size: CustomSpacerSize = CustomSpacerSize.MEDIUM) {
    Spacer(modifier = Modifier.height(size.height))
}

@Composable
fun HorizontalSpacer(size: CustomSpacerSize = CustomSpacerSize.MEDIUM) {
    Spacer(modifier = Modifier.width(size.height))
}