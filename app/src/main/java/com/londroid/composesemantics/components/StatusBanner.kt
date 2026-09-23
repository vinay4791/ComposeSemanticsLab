package com.londroid.composesemantics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

/**
 * Mirrors the talk's CartCheckoutStatus example: a live region that
 * announces payment state changes to screen readers without the user
 * needing to re-explore the screen.
 */
@Composable
fun StatusBanner(isSuccess: Boolean?, focusRequester: FocusRequester) {
    val label = when (isSuccess) {
        true -> "Payment Successful"
        false -> "Payment Failed"
        null -> "No payment attempted yet"
    }
    val background = when (isSuccess) {
        true -> MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
        false -> MaterialTheme.colorScheme.error.copy(alpha = 0.18f)
        null -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .focusable()
            .semantics {
                liveRegion = LiveRegionMode.Polite
                contentDescription = label
            }
            .background(background, RoundedCornerShape(12.dp))
            .padding(20.dp),
    ) {
        Text(label, style = MaterialTheme.typography.titleMedium)
    }
}
