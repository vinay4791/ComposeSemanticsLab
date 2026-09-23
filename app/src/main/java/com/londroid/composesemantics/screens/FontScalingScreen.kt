package com.londroid.composesemantics.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.londroid.composesemantics.components.DemoScaffold
import com.londroid.composesemantics.components.FixToggleRow
import com.londroid.composesemantics.components.HowToVerifyCard

private const val SampleLabel =
    "Account Balance — \$12,480.50 across 2 linked accounts"

@Composable
fun FontScalingScreen(onBack: () -> Unit) {
    var flexibleFixApplied by remember { mutableStateOf(false) }
    var fontScale by remember { mutableFloatStateOf(1f) }

    DemoScaffold(title = "Font Scaling", onBack = onBack) {
        item {
            Text(
                text = "Drag the slider to simulate large system font sizes, then flip the switch " +
                    "to see heightIn(min = ...) rescue a fixed-height row.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        item {
            Text(
                text = "Simulated font scale: ${String.format("%.1f", fontScale)}x",
                style = MaterialTheme.typography.labelLarge,
            )
            Slider(
                value = fontScale,
                onValueChange = { fontScale = it },
                valueRange = 1f..2.5f,
            )
        }
        item {
            FixToggleRow(
                label = if (flexibleFixApplied) "heightIn(min = 56.dp) (fixed)" else "height(56.dp) (broken)",
                checked = flexibleFixApplied,
                onCheckedChange = { flexibleFixApplied = it },
            )
        }
        item {
            val density = LocalDensity.current
            CompositionLocalProvider(LocalDensity provides Density(density.density, fontScale)) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .padding(20.dp)
                            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.primary), RoundedCornerShape(8.dp))
                            .padding(4.dp),
                    ) {
                        if (flexibleFixApplied) {
                            FlexibleBankRow()
                        } else {
                            FixedBankRow()
                        }
                    }
                }
            }
        }
        item {
            HowToVerifyCard(
                "Drag the slider up with the switch off: the text spills past its bordered box " +
                    "because the row's height is locked at 56dp. Flip the switch on and the box " +
                    "grows with the text instead.",
            )
        }
    }
}

/** BAD: fixed height clips/overflows once the system font scale grows. */
@Composable
private fun FixedBankRow() {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(SampleLabel, fontSize = 16.sp)
    }
}

/** GOOD: flexible constraints let the row grow with the text. */
@Composable
private fun FlexibleBankRow() {
    Row(
        modifier = Modifier
            .heightIn(min = 56.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = SampleLabel,
            fontSize = 16.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
