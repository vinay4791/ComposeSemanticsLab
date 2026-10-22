# Compose Semantics Lab

A companion app for the talks **"Beyond contentDescription: Demystifying Compose
Semantics for Production Apps"** (Londroid) and **"Beyond Pixel Perfect: Designing
Invisible UI for Screen Readers"** (IxDF Barcelona). Every case from either deck is
its own screen here, with a live BAD/GOOD toggle so you can feel the difference with
TalkBack turned on, not just read about it.

## Automated UI Testing

| # | Screen | Slide topic | What it shows |
|---|--------|--------------|----------------|
| 1 | Node Merging | Solution 1 — `mergeDescendants` | A product card toggles between 4 separate TalkBack stops and one merged stop with a custom `stateDescription`. |
| 2 | Clear & Set Semantics | Solution 2 — `clearAndSetSemantics` | A 5-star rating bar toggles between 5 noisy icon announcements and one clean "Rating: 4 out of 5 stars." |
| 3 | Live Regions & Focus | Dynamic state / live regions | Buttons flip a payment result; `LiveRegionMode.Polite` + `FocusRequester` announce and focus the result automatically. |
| 4 | Font Scaling | 200%+ font scaling | A slider simulates system font scale from 1x–2.5x; toggle between `Modifier.height()` (text gets silently truncated) and `Modifier.heightIn(min = ...)` (row grows, text wraps with an ellipsis). |
| 5 | Merge Scenarios | The `Text("Hello")` trace | The exact same `Text("Hello")` composable in a plain `Column` vs. a `clickable` `Row`, so you can feel how its container decides whether it stays independent or gets merged. |
| 6 | Semantics Defaults | "Which elements have semantics by default?" | Live examples of what does (`Text`, `clickable`, explicit `Modifier.semantics`) and doesn't (`Box`/`Column`/`Row`, `Icon(contentDescription = null)`, `Canvas`) get a `SemanticsConfiguration` for free. |
| 7 | Reading Order | IxDF Barcelona — "Custom Reading Order (Traversal Index)" | The same product card, pixel-for-pixel identical, toggles `Modifier.semantics { traversalIndex = -1f }` on the price so it's announced first by TalkBack without moving anywhere on screen. |
| 8 | Traversal Group | IxDF Barcelona — "Traversal Groups (Focus Islands)" | A horizontally scrolling photo carousel nested in a card, followed by an unrelated card below. Toggles `Modifier.semantics { isTraversalGroup = true }` so TalkBack can't leak out of the carousel into the next card mid-swipe. |

It asserts against the **merged tree** (`useUnmergedTree = false`) and contrasts it with the **unmerged tree** (`useUnmergedTree = true`) using the real `PrimaryProductCard` composable from Screen 1.

---

## Project Structure

```text
app/src/main/java/com/londroid/composesemantics/
  ├── MainActivity.kt
  ├── navigation/
  │     └── ComposeSemanticsNavHost.kt   # Routes for all 8 screens
  ├── screens/                           # One file per demo screen
  ├── components/                        # ProductCard, RatingBar, StatusBanner, DemoScaffold, CaseListItem, Badges
  └── ui/theme/
        └── Theme.kt                      # Dark navy / teal palette matching slide deck
app/src/androidTest/java/com/londroid/composesemantics/
  └── ProductCardSemanticsTest.kt
