package com.londroid.composesemantics.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.unit.dp

/**
 * A single star. On its own it carries a real contentDescription — realistic
 * for a reusable icon component — which is exactly what makes a naive rating
 * bar noisy once you place five of them in a row.
 */
@Composable
fun StarIcon(filled: Boolean) {
    Icon(
        imageVector = Icons.Filled.Star,
        contentDescription = if (filled) "Filled star" else "Empty star",
        tint = if (filled) {
            MaterialTheme.colorScheme.secondary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
        },
        modifier = Modifier.size(28.dp),
    )
}

/** BAD: no override — each star announces itself individually. */
@Composable
fun NoisyRatingBar(filledCount: Int) {
    Row {
        repeat(5) { index -> StarIcon(filled = index < filledCount) }
    }
}

/** GOOD: clearAndSetSemantics wipes the five child nodes and supplies one clean description. */
@Composable
fun CleanRatingBar(filledCount: Int) {
    Row(
        modifier = Modifier.clearAndSetSemantics {
            contentDescription = "Rating: $filledCount out of 5 stars"
            role = Role.Button
        },
    ) {
        repeat(5) { index -> StarIcon(filled = index < filledCount) }
    }
}
