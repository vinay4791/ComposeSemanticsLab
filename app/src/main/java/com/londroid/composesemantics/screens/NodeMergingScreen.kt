package com.londroid.composesemantics.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.londroid.composesemantics.components.DemoScaffold
import com.londroid.composesemantics.components.FixToggleRow
import com.londroid.composesemantics.components.HowToVerifyCard
import com.londroid.composesemantics.components.PrimaryProductCard
import com.londroid.composesemantics.components.ProductCardUnmerged

@Composable
fun NodeMergingScreen(onBack: () -> Unit) {
    var mergeFixApplied by remember { mutableStateOf(false) }

    DemoScaffold(title = "Node Merging", onBack = onBack) {
        item {
            Text(
                text = "The same product card, rendered two ways. Flip the switch to compare " +
                    "how many TalkBack stops it takes to get past one card.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        item {
            FixToggleRow(
                label = if (mergeFixApplied) "mergeDescendants = true (fixed)" else "No semantics override (broken)",
                checked = mergeFixApplied,
                onCheckedChange = { mergeFixApplied = it },
            )
        }
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                if (mergeFixApplied) {
                    PrimaryProductCard(
                        title = "Wireless Headphones",
                        price = "$99.00",
                        status = "In stock, ready to ship",
                        onClick = {},
                    )
                } else {
                    ProductCardUnmerged(
                        title = "Wireless Headphones",
                        price = "$99.00",
                        status = "In stock, ready to ship",
                    )
                }
            }
        }
        items(explanationBullets(mergeFixApplied)) { line ->
            Text("• $line", style = MaterialTheme.typography.bodyMedium)
        }
        item {
            HowToVerifyCard(
                "Enable TalkBack, then swipe onto the card above. Broken: you'll feel 4 separate " +
                    "stops (image, title, price, status). Fixed: one stop reads everything at once.",
            )
        }
    }
}

private fun explanationBullets(fixed: Boolean): List<String> = if (fixed) {
    listOf(
        "Row carries .clickable { } and Modifier.semantics(mergeDescendants = true)",
        "A custom stateDescription (\"In stock, ready to ship\") is appended to the merged label",
        "Children no longer expose their own semantics nodes",
    )
} else {
    listOf(
        "Row has no clickable modifier and no semantics override",
        "Image, title, price, and status are each their own SemanticsNode",
        "A screen reader has to visit all of them individually",
    )
}
