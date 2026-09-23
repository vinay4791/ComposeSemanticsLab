package com.londroid.composesemantics.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.londroid.composesemantics.components.CaseListItem
import com.londroid.composesemantics.navigation.Routes

private data class Case(val route: String, val title: String, val subtitle: String)

private val cases = listOf(
    Case(Routes.NODE_MERGING, "Node Merging", "mergeDescendants: 5 stops → 1"),
    Case(Routes.CLEAR_SEMANTICS, "Clear & Set Semantics", "Wipe noisy child nodes"),
    Case(Routes.LIVE_REGION, "Live Regions & Focus", "Announce async state changes"),
    Case(Routes.FONT_SCALING, "Font Scaling", "heightIn() vs. fixed height"),
    Case(Routes.MERGE_SCENARIOS, "Merge Scenarios", "Text(\"Hello\") in two contexts"),
    Case(Routes.SEMANTICS_DEFAULTS, "Semantics Defaults", "What gets semantics for free"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Compose Semantics Lab") })
        },
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Every case from the talk, live. Turn on TalkBack and try each screen.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = padding.calculateTopPadding() + 12.dp,
                        bottom = 4.dp,
                    ),
            )
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(cases) { case ->
                    CaseListItem(
                        number = cases.indexOf(case) + 1,
                        title = case.title,
                        subtitle = case.subtitle,
                        onClick = { onNavigate(case.route) },
                    )
                }
            }
        }
    }
}
