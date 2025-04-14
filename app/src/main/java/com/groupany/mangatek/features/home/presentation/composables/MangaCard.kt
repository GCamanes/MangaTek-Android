package com.groupany.mangatek.features.home.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.groupany.mangatek.R
import com.groupany.mangatek.core.constants.AppDimension
import com.groupany.mangatek.core.domain.entities.MangaLightEntity
import com.groupany.mangatek.core.presentation.composable.CustomSpacerSize
import com.groupany.mangatek.core.presentation.composable.VerticalSpacer

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MangaCard(
    manga: MangaLightEntity,
    isFavorite: Boolean = false,
    onToggle: (String) -> Unit,
    getCachedUrl: (String) -> String?,
    getDownloadUrl: suspend (String) -> String?
) {
    val cachedUrl = remember(manga.coverPath) { getCachedUrl(manga.coverPath) }
    val imageUrl by produceState(initialValue = cachedUrl, key1 = manga.coverPath) {
        if (cachedUrl == null) {
            value = getDownloadUrl(manga.coverPath)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(AppDimension.MangaCardHeight),
        shape = RoundedCornerShape(AppDimension.CornerRound),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd,
        ) {
            Row {
                if (imageUrl != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(500)
                            .build(),
                        contentDescription = manga.title,
                        modifier = Modifier
                            .width(AppDimension.MangaCardWidth)
                            .height(AppDimension.MangaCardHeight),
                        contentScale = ContentScale.FillHeight
                    )
                } else {
                    // Placeholder or loading
                    Box(
                        modifier = Modifier
                            .width(AppDimension.MangaCardWidth)
                            .height(AppDimension.MangaCardHeight),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(30.dp))
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = AppDimension.PaddingSmall)
                ) {
                    Text(
                        modifier = Modifier.padding(start = AppDimension.PaddingSmall, end = 48.dp),
                        text = manga.title,
                        maxLines = 2,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    VerticalSpacer(CustomSpacerSize.SMALL)

                    FlowRow (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppDimension.PaddingSmall)
                    ){
                        manga.getFilteredAuthors().forEach { author ->
                            Box (modifier = Modifier.padding(end = AppDimension.PaddingMedium)){
                                Text(
                                    author,
                                    maxLines = 1,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
            IconButton(onClick = { onToggle(manga.id) }) {
                Icon(
                    if (isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = stringResource(R.string.settings),
                    modifier = Modifier.size(AppDimension.IconHeight),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd,
            ) {
                MangaStatus(manga)
            }
        }
    }
}

@Composable
fun MangaStatus(manga: MangaLightEntity) {
    val color = if (manga.isOnGoing()) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.secondary

    Row (
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                shape = RoundedCornerShape(topStart = AppDimension.CornerRound)
            )
            .padding(start = AppDimension.PaddingSmall)
            .height(AppDimension.MangaStatusHeight),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = manga.lastChapter,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(end = AppDimension.PaddingSmall)
        )
        Box(modifier = Modifier.size(AppDimension.MangaStatusHeight)) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val path = Path().apply {
                    moveTo(size.width, 0f)
                    lineTo(size.width, size.height) // To bottom-right corner
                    lineTo(0f, size.height)          // To the top-right corner
                    close()                         // Close path to return to top-left corner
                }
                drawPath(path = path, color = color)
            }
        }
    }
}