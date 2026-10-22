package com.londroid.composesemantics.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import com.londroid.composesemantics.components.DemoScaffold
import com.londroid.composesemantics.components.FixToggleRow
import com.londroid.composesemantics.components.HowToVerifyCard
import com.londroid.composesemantics.components.ProductThumbnail

/**
 * Mirrors the IxDF Barcelona "Custom Reading Order (Traversal Index)" slide:
 * the same visual layout, but a semantics-only override moves the price to
 * the front of the TalkBack reading order without moving a single pixel.
 */
@Composable
fun ReadingOrderScreen(onBack: () -> Unit) {
    var priorityOrderApplied by remember { mutableStateOf(false) }

    DemoScaffold(title = "Reading Order", onBack = onBack) {
        item {
            Text(
                text = "Same card, same visual layout. Flip the switch to move the price to the " +
                    "front of the TalkBack reading order — without moving a single pixel.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        item {
            FixToggleRow(
                label = if (priorityOrderApplied) {
                    "traversalIndex = -1f on price (fixed)"
                } else {
                    "Default order (price read last)"
                },
                checked = priorityOrderApplied,
                onCheckedChange = { priorityOrderApplied = it },
            )
        }
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ProductThumbnail()
                    Text(
                        text = "Wireless Headphones",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 12.dp),
                    )
                    Row(modifier = Modifier.padding(top = 4.dp)) {
                        repeat(5) { index ->
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = if (index < 4) {
                                    MaterialTheme.colorScheme.secondary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                                },
                                modifier = Modifier.size(20.dp),
                            )
                        }
                    }
                    Text(
                        text = "$99.00",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .semantics {
                                traversalIndex = if (priorityOrderApplied) -1f else 0f
                            },
                    )
                }
            }
        }
        items(explanationBullets(priorityOrderApplied)) { line ->
            Text("• $line", style = MaterialTheme.typography.bodyMedium)
        }
        item {
            HowToVerifyCard(
                "Enable TalkBack and swipe onto the card. Default: thumbnail, title, rating, then " +
                    "price last. Fixed: price is announced first, even though it's still the last " +
                    "element on screen.",
            )
        }
    }
}

private fun explanationBullets(fixed: Boolean): List<String> = if (fixed) {
    listOf(
        "Price carries Modifier.semantics { traversalIndex = -1f }",
        "A lower traversalIndex is read earlier, regardless of visual position",
        "Nothing else in the layout changes — purely a reading-order override",
    )
} else {
    listOf(
        "No traversalIndex override — every element defaults to 0f",
        "TalkBack falls back to natural layout order: top to bottom",
        "Price, the decision-making fact, is announced dead last",
    )
}
