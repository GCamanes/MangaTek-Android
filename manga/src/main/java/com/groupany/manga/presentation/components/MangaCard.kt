package com.groupany.manga.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.groupany.manga.domain.entities.MangaLightEntity
import com.groupany.ui.components.CustomSpacerSize
import com.groupany.ui.components.ToggleIconButton
import com.groupany.ui.components.VerticalSpacer
import com.groupany.ui.constants.UIConstants

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MangaCard(
    modifier: Modifier = Modifier,
    manga: MangaLightEntity,
    isFavorite: Boolean = false,
    onClick: () -> Unit,
    onToggle: (String) -> Unit,
    getCachedUrl: (String) -> String?,
    getDownloadUrl: suspend (String) -> String?
) {
    val imageUrl by produceState<String?>(initialValue = null, key1 = manga.id) {
        value = getCachedUrl(manga.coverPath) ?: getDownloadUrl(manga.coverPath)
    }

    Column(
        modifier = modifier
            .clickable(
                enabled = imageUrl != null,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.7f),
            shape = RoundedCornerShape(UIConstants.CornerRound),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                if (imageUrl != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(300)
                            .build(),
                        contentDescription = manga.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(30.dp))
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomStart = UIConstants.CornerRound))
                        .background(color = Color.Black.copy(alpha = 0.7f))
                ) {
                    ToggleIconButton(
                        isSelected = isFavorite,
                        selectedIcon = Icons.Outlined.Favorite,
                        unselectedIcon = Icons.Outlined.FavoriteBorder,
                        contentDescription = "add to favorites"
                    ) { onToggle(manga.id) }
                }


                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd,
                ) {
                    MangaStatus(manga)
                }
            }
        }

        VerticalSpacer(CustomSpacerSize.EXTRA_SMALL)

        Text(
            modifier = Modifier.padding(horizontal = UIConstants.PaddingSmall),
            text = manga.title,
            maxLines = 2,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
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