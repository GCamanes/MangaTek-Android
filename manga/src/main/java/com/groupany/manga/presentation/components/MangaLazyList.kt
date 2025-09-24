package com.groupany.manga.presentation.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.groupany.manga.domain.entities.MangaLightEntity
import com.groupany.ui.constants.UIConstants

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MangaLazyList(
    state: LazyGridState,
    mangaList: List<MangaLightEntity> = emptyList(),
    isFavorite: (String) -> Boolean,
    onMangaClick: (id: String, title: String, coverUrl: String) -> Unit,
    onToggle: (String) -> Unit,
    getCachedUrl: (String) -> String?,
    getDownloadUrl: suspend (String) -> String?,
) {
    LazyVerticalGrid(
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
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(UIConstants.PaddingMedium),
        verticalArrangement = Arrangement.spacedBy(UIConstants.PaddingLarge)
    ) {
        items(mangaList.size) { index ->
            MangaCard(
                mangaList[index],
                isFavorite(mangaList[index].id),
                onClick = {
                    onMangaClick(
                        mangaList[index].id,
                        mangaList[index].title,
                        getCachedUrl(mangaList[index].coverPath)!!,
                    )
                },
                onToggle = onToggle,
                getCachedUrl = getCachedUrl,
                getDownloadUrl = getDownloadUrl
            )
        }
    }
}