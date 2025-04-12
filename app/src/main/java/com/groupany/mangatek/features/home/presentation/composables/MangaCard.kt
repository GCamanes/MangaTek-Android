package com.groupany.mangatek.features.home.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.groupany.mangatek.core.constants.AppDimension
import com.groupany.mangatek.core.domain.entities.MangaLightEntity
import com.groupany.mangatek.core.presentation.composable.CustomSpacerSize
import com.groupany.mangatek.core.presentation.composable.VerticalSpacer

@Composable
fun MangaCard(manga: MangaLightEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column (
            modifier = Modifier.fillMaxWidth().padding(AppDimension.PaddingMedium)
        ) {
            Text(text = manga.title, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
            VerticalSpacer(CustomSpacerSize.SMALL)
            Text(text = manga.status, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}