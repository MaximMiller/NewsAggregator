package com.example.newsaggregator.feature.newsfeed.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsaggregator.R

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onFavoriteClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = stringResource(R.string.cd_favorite_icon),
            tint = if (isFavorite)
                MaterialTheme.colorScheme.error
            else
                MaterialTheme.colorScheme.onSurface.copy(red = 0.6f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteButtonPreview() {
    MaterialTheme {
        Row {
            FavoriteButton(
                isFavorite = false,
                onFavoriteClick = {}
            )
            Spacer(Modifier.width(8.dp))
            FavoriteButton(
                isFavorite = true,
                onFavoriteClick = {}
            )
        }
    }
}