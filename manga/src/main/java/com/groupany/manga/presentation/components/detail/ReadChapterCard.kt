package com.groupany.manga.presentation.components.detail

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.groupany.localization.R
import com.groupany.ui.components.CustomSpacerSize
import com.groupany.ui.components.VerticalSpacer
import com.groupany.ui.constants.UIConstants
import com.groupany.ui.theme.MangaTekTheme

@OptIn(ExperimentalLayoutApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun ReadChapterCard(
    mangaId: String,
    chapterId: String,
    onClick: (mangaId: String, chapterId: String) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(UIConstants.CornerRound),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = { onClick(mangaId, chapterId) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(UIConstants.PaddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    stringResource(R.string.startReading),
                    style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.onSurfaceVariant)
                )

                VerticalSpacer(CustomSpacerSize.SMALL)

                Text(
                    "${stringResource(R.string.chapter)} $chapterId",
                    style = MaterialTheme.typography.titleMedium.copy(MaterialTheme.colorScheme.onSurface)
                )
            }
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = "Start reading chapter $chapterId of $mangaId",
                modifier = Modifier.size(UIConstants.IconHeight),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview("default", "round")
@Composable
private fun GenreTagPreview() {
    MangaTekTheme {
        ReadChapterCard(
            "akira", chapterId = "0001",
            onClick = { mid, cid -> }
        )
    }
}
