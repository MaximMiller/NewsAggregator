package com.example.newsaggregator.feature.newsfeed.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.newsaggregator.R

@Composable
 fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.surface.copy(alpha = 0.9f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                color = colorScheme.primary,
                trackColor = colorScheme.surfaceVariant,
                strokeCap = StrokeCap.Round,
                strokeWidth = 6.dp
            )
            Text(
                text = stringResource(R.string.loading),
                style = typography.bodyLarge,
                color = colorScheme.onSurface
            )
        }
    }
}