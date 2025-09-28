package com.groupany.manga.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.groupany.ui.constants.UIConstants
import com.groupany.ui.theme.MangaTekTheme

@OptIn(ExperimentalLayoutApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun GenreTag(label: String) {
    Card(
        shape = RoundedCornerShape(UIConstants.CornerRound),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.75f)
        )
    ) {
        Text(
            label,
            modifier = Modifier.padding(
                horizontal = UIConstants.PaddingSmall,
                vertical = UIConstants.PaddingExtraSmall
            ),
            style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.primary)
        )
    }
}

@Preview("default", "round")
@Preview("dark theme", "round", uiMode = UI_MODE_NIGHT_YES)
@Preview("large font", "round", fontScale = 2f)
@Composable
private fun GenreTagPreview() {
    MangaTekTheme {
        GenreTag("Test")
    }
}

@Preview("default", "round")
@Preview("dark theme", "round", uiMode = UI_MODE_NIGHT_YES)
@Preview("large font", "round", fontScale = 2f)
@Composable
private fun GenreTagInRowPreview() {
    val list: List<String> = listOf("shonen", "action", "horror")
    MangaTekTheme {
        Row {
            list.map { it -> GenreTag(it) }
        }
    }
}
