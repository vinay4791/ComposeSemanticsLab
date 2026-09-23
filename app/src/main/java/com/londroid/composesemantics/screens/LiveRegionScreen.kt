package com.londroid.composesemantics.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.londroid.composesemantics.components.DemoScaffold
import com.londroid.composesemantics.components.HowToVerifyCard
import com.londroid.composesemantics.components.StatusBanner

@Composable
fun LiveRegionScreen(onBack: () -> Unit) {
    var status by remember { mutableStateOf<Boolean?>(null) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(status) {
        if (status != null) {
            focusRequester.requestFocus()
        }
    }

    DemoScaffold(title = "Live Regions & Focus", onBack = onBack) {
        item {
            Text(
                text = "Trigger a state change and watch (or hear) it get announced immediately, " +
                    "the way a real payment result would.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        item {
            StatusBanner(isSuccess = status, focusRequester = focusRequester)
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Button(onClick = { status = true }, modifier = Modifier.weight(1f)) {
                    Text("Simulate Success")
                }
                OutlinedButton(onClick = { status = false }, modifier = Modifier.weight(1f)) {
                    Text("Simulate Failure")
                }
            }
        }
        item {
            HowToVerifyCard(
                "Enable TalkBack, then tap a button without touching the banner. " +
                    "LiveRegionMode.Polite announces the new state on its own, and the " +
                    "FocusRequester moves accessibility focus onto the banner.",
            )
        }
    }
}
