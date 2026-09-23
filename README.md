# Compose Semantics Lab

A companion app for the talk **"Beyond contentDescription: Demystifying Compose
Semantics for Production Apps."** Every case from the slides is its own
screen here, with a live BAD/GOOD toggle so you can feel the difference with
TalkBack turned on, not just read about it.

## Screens

| # | Screen | Slide topic | What it shows |
|---|--------|--------------|----------------|
| 1 | Node Merging | Solution 1 — `mergeDescendants` | A product card toggles between 4 separate TalkBack stops and one merged stop with a custom `stateDescription`. |
| 2 | Clear & Set Semantics | Solution 2 — `clearAndSetSemantics` | A 5-star rating bar toggles between 5 noisy icon announcements and one clean "Rating: 4 out of 5 stars." |
| 3 | Live Regions & Focus | Dynamic state / live regions | Buttons flip a payment result; `LiveRegionMode.Polite` + `FocusRequester` announce and focus the result automatically. |
| 4 | Font Scaling | 200%+ font scaling | A slider simulates system font scale from 1x–2.5x; toggle between `Modifier.height()` (text gets silently truncated) and `Modifier.heightIn(min = ...)` (row grows, text wraps with an ellipsis). |
| 5 | Merge Scenarios | The `Text("Hello")` trace | The exact same `Text("Hello")` composable in a plain `Column` vs. a `clickable` `Row`, so you can feel how its container decides whether it stays independent or gets merged. |
| 6 | Semantics Defaults | "Which elements have semantics by default?" | Live examples of what does (`Text`, `clickable`, explicit `Modifier.semantics`) and doesn't (`Box`/`Column`/`Row`, `Icon(contentDescription = null)`, `Canvas`) get a `SemanticsConfiguration` for free. |

There's also an instrumented test, `ProductCardSemanticsTest`, mirroring the
talk's "Automating Assertions with SemanticsMatcher" slide — it asserts
against the **merged** tree (`useUnmergedTree = false`) and contrasts it with
the **unmerged** tree (`useUnmergedTree = true`) on the real `PrimaryProductCard`
composable used by screen 1.

## Project layout

```
app/src/main/java/com/londroid/composesemantics/
  MainActivity.kt
  navigation/ComposeSemanticsNavHost.kt   # routes for all 6 screens
  screens/                                 # one file per screen above
  components/                              # ProductCard, RatingBar, StatusBanner,
                                            # DemoScaffold, CaseListItem, Badges
  ui/theme/Theme.kt                        # dark navy / teal palette matching the slides
app/src/androidTest/java/com/londroid/composesemantics/
  ProductCardSemanticsTest.kt
```

## Running it

Open the project root in Android Studio and hit Run — it's a standard
single-module Compose app (`applicationId` `com.londroid.composesemantics`).

From the command line:

```bash
./gradlew assembleDebug          # build the APK
./gradlew connectedDebugAndroidTest   # run the semantics test on a connected device/emulator
```

This project was built and verified end-to-end in this environment: it
compiles, installs, and runs on a Pixel 9 Pro (API 35) emulator, and both
instrumented tests pass for real (not just "should compile").

## Toolchain

- AGP 9.4.1 (built-in Kotlin support — no separate `kotlin-android` plugin needed)
- Kotlin 2.4.20 (`org.jetbrains.kotlin.plugin.compose` for the Compose compiler)
- Compose BOM 2026.09.00
- `compileSdk` / `targetSdk` 37, `minSdk` 24

## Trying it with TalkBack

Turn on TalkBack (Settings → Accessibility → TalkBack) and swipe through each
screen. Every "How to verify" card at the bottom of a screen tells you exactly
what to listen for in the BAD state vs. the GOOD state.
