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
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.groupany.mangatek.core.domain.entities.MangaLightEntity
import com.groupany.ui.components.CustomSpacerSize
import com.groupany.ui.components.ToggleIconButton
import com.groupany.ui.components.VerticalSpacer
import com.groupany.ui.constants.UIConstants

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MangaCard(
    manga: MangaLightEntity,
    isFavorite: Boolean = false,
    onToggle: (String) -> Unit,
    getCachedUrl: (String) -> String?,
    getDownloadUrl: suspend (String) -> String?
) {
    val imageUrl by produceState<String?>(initialValue = null, key1 = manga.id) {
        value = getCachedUrl(manga.coverPath) ?: getDownloadUrl(manga.coverPath)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(UIConstants.MangaCardHeight),
        shape = RoundedCornerShape(UIConstants.CornerRound),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
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
                            .width(UIConstants.MangaCardWidth)
                            .height(UIConstants.MangaCardHeight),
                        contentScale = ContentScale.FillHeight
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .width(UIConstants.MangaCardWidth)
                            .height(UIConstants.MangaCardHeight),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(30.dp))
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = UIConstants.PaddingSmall)
                ) {
                    Text(
                        modifier = Modifier.padding(start = UIConstants.PaddingSmall, end = 48.dp),
                        text = manga.title,
                        maxLines = 2,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    VerticalSpacer(CustomSpacerSize.SMALL)

                    FlowRow (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = UIConstants.PaddingSmall)
                    ){
                        manga.getFilteredAuthors().forEach { author ->
                            Box (modifier = Modifier.padding(end = UIConstants.PaddingMedium)){
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
            ToggleIconButton(
                isSelected = isFavorite,
                selectedIcon = Icons.Outlined.Favorite,
                unselectedIcon = Icons.Outlined.FavoriteBorder,
                contentDescription = "add to favorites"
            ) { onToggle(manga.id) }
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
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = UIConstants.CornerRound)
            )
            .padding(start = UIConstants.PaddingSmall)
            .height(UIConstants.MangaStatusHeight),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = manga.lastChapter,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(end = UIConstants.PaddingSmall)
        )
        Box(modifier = Modifier.size(UIConstants.MangaStatusHeight)) {
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