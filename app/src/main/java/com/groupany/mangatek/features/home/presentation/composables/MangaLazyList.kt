package com.groupany.mangatek.features.home.presentation.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import com.groupany.mangatek.core.constants.AppDimension
import com.groupany.mangatek.core.domain.entities.MangaLightEntity

@Composable
fun MangaLazyList(
    state: LazyListState,
    mangaList: List<MangaLightEntity> = emptyList(),
    isFavorite: (String) -> Boolean,
    onToggle: (String) -> Unit,
    getCachedUrl: (String) -> String?,
    getDownloadUrl: suspend (String) -> String?
) {
    LazyColumn(
        state = state,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = AppDimension.PaddingMedium,
            start = AppDimension.PaddingMedium,
            end = AppDimension.PaddingMedium,
            bottom = AppDimension.PaddingBig
                    + AppDimension.PaddingLarge
                    + AppDimension.PaddingSmall,
        ),
        verticalArrangement = Arrangement.spacedBy(AppDimension.PaddingMedium)
    ) {
        items(mangaList) {
            manga -> MangaCard(
                manga,
                isFavorite(manga.id),
                onToggle = onToggle,
                getCachedUrl = getCachedUrl,
                getDownloadUrl = getDownloadUrl
            )
        }
    }
}