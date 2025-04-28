package com.groupany.mangatek.features.home.presentation.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import com.groupany.mangatek.core.domain.entities.MangaLightEntity
import com.groupany.ui.constants.UIConstants

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
            top = UIConstants.PaddingMedium,
            start = UIConstants.PaddingMedium,
            end = UIConstants.PaddingMedium,
            bottom = UIConstants.PaddingBig
                    + UIConstants.PaddingLarge
                    + UIConstants.PaddingSmall,
        ),
        verticalArrangement = Arrangement.spacedBy(UIConstants.PaddingMedium)
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