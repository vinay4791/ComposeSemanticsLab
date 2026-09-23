package com.londroid.composesemantics.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.londroid.composesemantics.components.DemoScaffold
import com.londroid.composesemantics.components.HowToVerifyCard

@Composable
fun MergeScenariosScreen(onBack: () -> Unit) {
    var tapCount by remember { mutableStateOf(0) }

    DemoScaffold(title = "Merge Scenarios", onBack = onBack) {
        item {
            Text(
                text = "The exact same Text(\"Hello\") composable, placed in two different " +
                    "parents. Its fate in the merged tree depends entirely on its container.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        item {
            Text("Scenario A — Plain Container", style = MaterialTheme.typography.titleSmall)
            Card(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Hello")
                }
            }
            Text(
                text = "No mergeDescendants — stays an independent SemanticsNode. " +
                    "TalkBack focuses \"Hello\" on its own.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
        item {
            Text("Scenario B — Clickable Container", style = MaterialTheme.typography.titleSmall)
            Card(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { tapCount++ }
                        .padding(20.dp),
                ) {
                    Text("Hello")
                }
            }
            Text(
                text = "Tapped $tapCount time(s). clickable() sets mergeDescendants = true, " +
                    "so \"Hello\" is absorbed into the row's single merged label.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
        item {
            HowToVerifyCard(
                "Enable TalkBack and swipe onto each card. Scenario A announces just \"Hello.\" " +
                    "Scenario B announces \"Hello, Button\" as one unit — tap it and it counts up.",
            )
        }
    }
}
