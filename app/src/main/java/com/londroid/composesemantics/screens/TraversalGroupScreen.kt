package com.londroid.composesemantics.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.londroid.composesemantics.components.DemoScaffold
import com.londroid.composesemantics.components.FixToggleRow
import com.londroid.composesemantics.components.HowToVerifyCard

/**
 * Mirrors the IxDF Barcelona "Traversal Groups (Focus Islands)" slide.
 *
 * The real-world trigger for this bug is a horizontally scrolling carousel
 * nested inside a card: without a traversal-group boundary, TalkBack's
 * geometric sort can interleave the carousel's items with whatever sits
 * below the card, because off-screen/virtualized items have ambiguous
 * bounds. isTraversalGroup = true forces the whole card — every photo
 * chip included — to be read as one contiguous block first.
 */
@Composable
fun TraversalGroupScreen(onBack: () -> Unit) {
    var islandFixApplied by remember { mutableStateOf(false) }

    DemoScaffold(title = "Traversal Group", onBack = onBack) {
        item {
            Text(
                text = "A horizontally scrolling photo carousel, nested inside a card, followed " +
                    "by an unrelated card below. Flip the switch to trap TalkBack inside the " +
                    "carousel's card until it's fully read.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        item {
            FixToggleRow(
                label = if (islandFixApplied) {
                    "isTraversalGroup = true (focus island)"
                } else {
                    "No isTraversalGroup (focus can leak)"
                },
                checked = islandFixApplied,
                onCheckedChange = { islandFixApplied = it },
            )
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (islandFixApplied) {
                            Modifier.semantics { isTraversalGroup = true }
                        } else {
                            Modifier
                        },
                    ),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Trending Destinations", style = MaterialTheme.typography.titleMedium)
                    LazyRow(
                        modifier = Modifier.padding(top = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        items(destinationChips) { chip ->
                            PhotoChip(label = chip.first, color = chip.second)
                        }
                    }
                }
            }
        }
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Related Article", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "5 budget tips for your next trip",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
        items(explanationBullets(islandFixApplied)) { line ->
            Text("• $line", style = MaterialTheme.typography.bodyMedium)
        }
        item {
            HowToVerifyCard(
                "Enable TalkBack and swipe through the gallery card. Broken: swiping can jump " +
                    "between photo chips and the Related Article card below mid-carousel. Fixed: " +
                    "TalkBack visits the title, then every photo chip in order, and only moves to " +
                    "Related Article once the gallery card is fully read.",
            )
        }
    }
}

@Composable
private fun PhotoChip(label: String, color: Color) {
    Box(
        modifier = Modifier
            .size(width = 96.dp, height = 72.dp)
            .background(color, RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = Color.White)
    }
}

private val destinationChips = listOf(
    "Lisbon" to Color(0xFF2ED9A0),
    "Kyoto" to Color(0xFF5B8DEF),
    "Nairobi" to Color(0xFFE08A3C),
    "Oslo" to Color(0xFF8B6AD1),
    "Cusco" to Color(0xFFD9607A),
    "Hanoi" to Color(0xFF3FB6C9),
)

private fun explanationBullets(fixed: Boolean): List<String> = if (fixed) {
    listOf(
        "The gallery Card carries Modifier.semantics { isTraversalGroup = true }",
        "Its title and every photo chip are treated as one contiguous traversal block",
        "TalkBack cannot move to Related Article until the whole card is read",
    )
} else {
    listOf(
        "No traversal-group boundary around the carousel card",
        "Off-screen/virtualized LazyRow items have ambiguous geometric bounds",
        "TalkBack's position-based sort can interleave them with the card below",
    )
}
