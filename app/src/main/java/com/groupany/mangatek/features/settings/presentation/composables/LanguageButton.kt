package com.groupany.mangatek.features.settings.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun LanguageButton(
    iconRes: Int, // Pass the drawable resource
    value: String,
    selectedValue: String,
    onClick: (String) -> Unit
) {
    val isSelected = value == selectedValue
    val borderWidth = if (isSelected) 2.dp else 0.dp
    val borderColor = if (isSelected) Color.Blue else Color.Transparent

    Box(
        modifier = Modifier
            .size(64.dp)
            .border(BorderStroke(borderWidth, borderColor), shape = RoundedCornerShape(8.dp))
            .clickable { onClick(value) }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconRes), // Load image from drawable
            contentDescription = "Icon",
            modifier = Modifier.size(40.dp)
        )
    }
}