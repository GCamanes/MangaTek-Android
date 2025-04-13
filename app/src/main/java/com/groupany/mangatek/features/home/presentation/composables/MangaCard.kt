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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import coil.compose.AsyncImage
import com.google.firebase.storage.FirebaseStorage
import com.groupany.mangatek.core.constants.AppDimension
import com.groupany.mangatek.core.domain.entities.MangaLightEntity
import com.groupany.mangatek.core.presentation.composable.CustomSpacerSize
import com.groupany.mangatek.core.presentation.composable.HorizontalSpacer
import com.groupany.mangatek.core.presentation.composable.VerticalSpacer

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MangaCard(manga: MangaLightEntity) {
    var imageUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(manga.coverPath) {
        val storageRef = FirebaseStorage.getInstance().reference.child(manga.coverPath)
        storageRef.downloadUrl.addOnSuccessListener { uri ->
            imageUrl = uri.toString()
        }.addOnFailureListener {
            imageUrl = null
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(AppDimension.MangaCardHeight),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd,
        ) {
            TriangleBanner(isOngoing = manga.isOnGoing())
            Box (
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd,
            ) {
                LastChapterText(manga.lastChapter)
            }
            Row {
                if (imageUrl != null) {
                    AsyncImage(
                        model = imageUrl,
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
                        .padding(top = AppDimension.PaddingSmall)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = AppDimension.PaddingSmall),
                        text = manga.title,
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
                                Text(author, style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TriangleBanner(isOngoing: Boolean, modifier: Modifier = Modifier) {
    val color = if (isOngoing) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.secondary

    Box(
        modifier = modifier
            .size(20.dp) // Size of the triangle (adjust as needed)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path().apply {
                lineTo(size.width, size.height) // To bottom-right corner
                lineTo(size.width, 0f)          // To the top-right corner
                close()                         // Close path to return to top-left corner
            }

            drawPath(path = path, color = color)
        }
    }
}

@Composable
fun LastChapterText(lastChapter: String) {
    Text(
        text = lastChapter,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(6.dp)
    )
}