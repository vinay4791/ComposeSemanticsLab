# Compose Semantics Lab

A companion app and demonstration codebase for the Londroid talk **"Beyond contentDescription: Demystifying Compose Semantics for Production Apps."**

Every case from the presentation slides is implemented as its own interactive screen, featuring a live **BAD / GOOD toggle** so you can hear and feel the difference with TalkBack enabled.

* 🔗 **My Post:** [View on LinkedIn](https://lnkd.in/p/eEY3YCDg)
* 📢 **Londroid Announcement:** [View on LinkedIn](https://www.linkedin.com/feed/update/urn:li:share:7506682433342320640/)
* 📊 **Presentation Slides:** [compose_semantics_talk.pptx](https://github.com/user-attachments/files/32644402/compose_semantics_talk.pptx)


---

## Screens Overview

| # | Screen / Slide Topic | What it shows |
|---|---|---|
| **1** | **Node Merging** *(Solution 1 — `mergeDescendants`)* | A product card toggles between 4 separate TalkBack stops and one merged stop with a custom `stateDescription`. |
| **2** | **Clear & Set Semantics** *(Solution 2 — `clearAndSetSemantics`)* | A 5-star rating bar toggles between 5 noisy icon announcements and one clean *"Rating: 4 out of 5 stars."* |
| **3** | **Live Regions & Focus** *(Dynamic state / live regions)* | Buttons flip a payment result; `LiveRegionMode.Polite` + `FocusRequester` announce and focus the result automatically. |
| **4** | **Font Scaling** *(200%+ font scaling)* | A slider simulates system font scale from 1x–2.5x; toggle between `Modifier.height()` (text gets silently truncated) and `Modifier.heightIn(min = ...)` (row grows, text wraps with an ellipsis). |
| **5** | **Merge Scenarios** *(The `Text("Hello")` trace)* | The exact same `Text("Hello")` composable in a plain `Column` vs. a clickable `Row`, showing how container context decides whether it stays independent or merges. |
| **6** | **Semantics Defaults** *(Which elements have semantics by default?)* | Live examples of elements that do (`Text`, `clickable`, explicit `Modifier.semantics`) vs. elements that do not (`Box`, `Column`, `Row`, `Icon(contentDescription = null)`, `Canvas`) get a `SemanticsConfiguration` for free. |

---

## Automated UI Testing

The project includes an instrumented test, `ProductCardSemanticsTest`, mirroring the talk's *"Automating Assertions with SemanticsMatcher"* slide. 

It asserts against the **merged tree** (`useUnmergedTree = false`) and contrasts it with the **unmerged tree** (`useUnmergedTree = true`) using the real `PrimaryProductCard` composable from Screen 1.

---

## Project Structure

```text
app/src/main/java/com/londroid/composesemantics/
  ├── MainActivity.kt
  ├── navigation/
  │     └── ComposeSemanticsNavHost.kt   # Routes for all 6 screens
  ├── screens/                           # One file per demo screen
  ├── components/                        # ProductCard, RatingBar, StatusBanner, DemoScaffold, CaseListItem, Badges
  └── ui/theme/
        └── Theme.kt                      # Dark navy / teal palette matching slide deck

app/src/androidTest/java/com/londroid/composesemantics/
  └── ProductCardSemanticsTest.kt
