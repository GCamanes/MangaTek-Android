package com.groupany.mangatek.features.home.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import coil.compose.AsyncImage
import com.google.firebase.storage.FirebaseStorage
import com.groupany.mangatek.core.constants.AppDimension
import com.groupany.mangatek.core.domain.entities.MangaLightEntity
import com.groupany.mangatek.core.presentation.composable.CustomSpacerSize
import com.groupany.mangatek.core.presentation.composable.VerticalSpacer

@Composable
fun MangaCard(manga: MangaLightEntity) {
    var imageUrl by remember { mutableStateOf<String?>(null) }

    // Fetch download URL
    LaunchedEffect(manga.coverPath) {
        val storageRef = FirebaseStorage.getInstance().reference.child(manga.coverPath)
        storageRef.downloadUrl.addOnSuccessListener { uri ->
            imageUrl = uri.toString()
        }.addOnFailureListener {
            // Handle the error (e.g., show placeholder)
            imageUrl = null
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row {
            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = manga.title,
                    modifier = Modifier
                        .width(80.dp)
                        .height(120.dp),
                    contentScale = ContentScale.FillHeight
                )
            } else {
                // Placeholder or loading
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(30.dp))
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppDimension.PaddingMedium)
            ) {
                Text(
                    text = manga.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                VerticalSpacer(CustomSpacerSize.SMALL)
                Text(
                    text = manga.status,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}