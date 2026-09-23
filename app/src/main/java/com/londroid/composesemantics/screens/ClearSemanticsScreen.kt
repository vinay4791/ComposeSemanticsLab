package com.londroid.composesemantics.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.londroid.composesemantics.components.CleanRatingBar
import com.londroid.composesemantics.components.DemoScaffold
import com.londroid.composesemantics.components.FixToggleRow
import com.londroid.composesemantics.components.HowToVerifyCard
import com.londroid.composesemantics.components.NoisyRatingBar

@Composable
fun ClearSemanticsScreen(onBack: () -> Unit) {
    var clearFixApplied by remember { mutableStateOf(false) }
    val filledCount = 4

    DemoScaffold(title = "Clear & Set Semantics", onBack = onBack) {
        item {
            Text(
                text = "Five star icons, one rating. Flip the switch to see clearAndSetSemantics " +
                    "wipe the noise and supply a single clean description.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        item {
            FixToggleRow(
                label = if (clearFixApplied) "clearAndSetSemantics (fixed)" else "No override (noisy)",
                checked = clearFixApplied,
                onCheckedChange = { clearFixApplied = it },
            )
        }
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.padding(20.dp)) {
                    if (clearFixApplied) {
                        CleanRatingBar(filledCount = filledCount)
                    } else {
                        NoisyRatingBar(filledCount = filledCount)
                    }
                }
            }
        }
        item {
            Text(
                text = if (clearFixApplied) {
                    "TalkBack: \"Rating: 4 out of 5 stars, Button.\" — one stop."
                } else {
                    "TalkBack: \"Filled star. Filled star. Filled star. Filled star. Empty star.\" — 5 stops."
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        item {
            HowToVerifyCard(
                "Enable TalkBack and swipe onto the stars. Broken: you'll hear all 5 icons " +
                    "individually. Fixed: you'll hear a single combined rating.",
            )
        }
    }
}
