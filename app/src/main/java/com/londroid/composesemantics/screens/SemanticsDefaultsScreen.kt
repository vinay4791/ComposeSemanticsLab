package com.londroid.composesemantics.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.londroid.composesemantics.components.DemoScaffold
import com.londroid.composesemantics.components.SemanticsBadge

@Composable
fun SemanticsDefaultsScreen(onBack: () -> Unit) {
    DemoScaffold(title = "Semantics Defaults", onBack = onBack) {
        item {
            Text(
                text = "What gets a SemanticsConfiguration for free — and what stays " +
                    "invisible to accessibility tools unless you add it yourself.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        item {
            Text("Have semantics", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
        }
        item {
            ExampleCard(
                title = "Text composable",
                hasSemantics = true,
                caption = "Text() automatically attaches Text = [\"...\"] so it can be announced.",
            ) {
                Text("Sample text", style = MaterialTheme.typography.bodyMedium)
            }
        }
        item {
            ExampleCard(
                title = "clickable modifier",
                hasSemantics = true,
                caption = "clickable auto-attaches a role, a click action, and mergeDescendants = true.",
            ) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                        .clickable { }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                ) {
                    Text("Tap me", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        item {
            ExampleCard(
                title = "Modifier.semantics { }",
                hasSemantics = true,
                caption = "Explicit overrides you write yourself — contentDescription, stateDescription, heading.",
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                        .semantics { contentDescription = "Custom description" },
                )
            }
        }
        item {
            Text("No semantics", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.error)
        }
        item {
            ExampleCard(
                title = "Box / Column / Row / Spacer",
                hasSemantics = false,
                caption = "Plain layout containers with no click listener carry no SemanticsConfiguration.",
            ) {
                Row {
                    Box(modifier = Modifier.size(24.dp).background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)))
                    Column {}
                }
            }
        }
        item {
            ExampleCard(
                title = "Icon(contentDescription = null)",
                hasSemantics = false,
                caption = "A deliberate opt-out — tells Compose this graphic is decorative.",
            ) {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
            }
        }
        item {
            ExampleCard(
                title = "Canvas",
                hasSemantics = false,
                caption = "Low-level drawing has no semantics unless wrapped in Modifier.semantics.",
            ) {
                Canvas(modifier = Modifier.size(32.dp)) {
                    drawRoundRect(color = Color(0xFF2ED9C3), cornerRadius = CornerRadius(6.dp.toPx()))
                }
            }
        }
    }
}

@Composable
private fun ExampleCard(
    title: String,
    hasSemantics: Boolean,
    caption: String,
    preview: @Composable () -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.size(56.dp),
                contentAlignment = Alignment.Center,
            ) {
                preview()
            }
            Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(title, style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
                    SemanticsBadge(hasSemantics = hasSemantics)
                }
                Text(
                    text = caption,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
        }
    }
}
