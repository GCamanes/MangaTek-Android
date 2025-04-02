package com.groupany.mangatek.core.presentation.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.groupany.mangatek.core.constants.Dimension

enum class CustomSpacerSize(val height: Dp) {
    SMALL(Dimension.PaddingSmall),
    MEDIUM(Dimension.PaddingMedium),
    LARGE(Dimension.PaddingLarge),
    BIG(Dimension.PaddingBig)
}

@Composable
fun VerticalSpacer(size: CustomSpacerSize = CustomSpacerSize.MEDIUM) {
    Spacer(modifier = Modifier.height(size.height))
}

@Composable
fun HorizontalSpacer(size: CustomSpacerSize = CustomSpacerSize.MEDIUM) {
    Spacer(modifier = Modifier.width(size.height))
}