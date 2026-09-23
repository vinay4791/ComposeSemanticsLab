package com.londroid.composesemantics

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasStateDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import com.londroid.composesemantics.components.PrimaryProductCard
import org.junit.Rule
import org.junit.Test

/**
 * Mirrors "Automating Assertions with SemanticsMatcher" from the talk, run
 * against this project's real PrimaryProductCard instead of a slide snippet.
 */
class ProductCardSemanticsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mergedTree_collapsesCardIntoOneAccessibleNode() {
        composeTestRule.setContent {
            PrimaryProductCard(
                title = "Wireless Headphones",
                price = "$99.00",
                status = "In stock, ready to ship",
                onClick = {},
            )
        }

        // mergeDescendants = true absorbs the child Text nodes into the Row
        // itself, so the row is what carries the combined text, the click
        // action, and the custom stateDescription — not a separate child.
        composeTestRule
            .onNode(
                hasText("Wireless Headphones") and hasClickAction(),
                useUnmergedTree = false,
            )
            .assertIsDisplayed()
            .assert(hasStateDescription("In stock, ready to ship"))
    }

    @Test
    fun unmergedTree_stillExposesTheRawChildNode() {
        composeTestRule.setContent {
            PrimaryProductCard(
                title = "Wireless Headphones",
                price = "$99.00",
                status = "In stock, ready to ship",
                onClick = {},
            )
        }

        // With useUnmergedTree = true we see the tree the way it existed
        // before merging — the title Text is still its own node here.
        composeTestRule
            .onNode(hasText("Wireless Headphones"), useUnmergedTree = true)
            .assertIsDisplayed()
    }
}
