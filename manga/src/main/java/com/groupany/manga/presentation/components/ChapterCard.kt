package com.groupany.manga.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.groupany.ui.constants.UIConstants

@Composable
fun ChapterCard(
    mangaId: String,
    chapterId: String,
    onClick: (mangaId: String, chapterId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(UIConstants.CornerRound),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = { onClick(mangaId, chapterId) },
        modifier = modifier
            .fillMaxWidth()
            .height(UIConstants.PaddingBig)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = chapterId,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}


@Composable
fun EmptyChapterCard(
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(UIConstants.CornerRound),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(UIConstants.PaddingBig)
    ) {}
}