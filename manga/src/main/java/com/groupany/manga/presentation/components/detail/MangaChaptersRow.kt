package com.groupany.manga.presentation.components.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.groupany.manga.domain.entities.MangaEntity
import com.groupany.ui.components.HorizontalSpacer
import com.groupany.ui.constants.UIConstants

@Composable
fun MangaChaptersRow(
    columns: Int,
    manga: MangaEntity,
    chapters: List<String> = emptyList()
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = UIConstants.PaddingMedium),
    ) {
        HorizontalSpacer()
        ChapterCard(
            mangaId = manga.id,
            chapterId = chapters[0],
            onClick = { mid, cid -> },
            modifier = Modifier.weight(1f)
        )
        HorizontalSpacer()
        if (chapters.size >= columns - 1) {
            ChapterCard(
                mangaId = manga.id,
                chapterId = chapters[columns - 2],
                onClick = { mid, cid -> },
                modifier = Modifier.weight(1f)
            )
        } else {
            EmptyChapterCard(
                modifier = Modifier.weight(1f)
            )
        }
        HorizontalSpacer()
        if (chapters.size == columns) {
            ChapterCard(
                mangaId = manga.id,
                chapterId = chapters[columns - 1],
                onClick = { mid, cid -> },
                modifier = Modifier.weight(1f)
            )
        } else {
            EmptyChapterCard(
                modifier = Modifier.weight(1f)
            )
        }
        HorizontalSpacer()
    }
}
