# Phase 0: Static Compose Prototype (tasks.md)

## Task 1: Navigation Skeleton & Scaffold
**Status:** DONE
**Instructions for Agent:**
1. Read `wiki/architecture.md` and `.agents/skills/UI/compose-navigation/SKILL.md`.
2. Create `core/navigation/TopLevelRoute.kt` defining the 5 main routes (Home, Bible, Media, Prayer, Profile) with their corresponding Material Icons.
3. Create `core/navigation/AppNavHost.kt` to house the `NavHost` and empty placeholder Composables for the 5 screens.
4. Update `MainActivity.kt` to render a main `Scaffold` containing a `BottomAppBar` that iterates over `TopLevelRoute`. Hook the bottom bar to the `NavController`.
5. Yield to human for build verification.

## Task 2: Core Design System
**Status:** DONE
**Instructions for Agent:**
1. Read `spec/requirement.md` to review the visual brand (dark-first, premium, Tamil support).
2. Modify `core/designsystem/Color.kt` to establish a deep, dark-first color palette (dark backgrounds, subtle surface colors, readable primary accents).
3. Modify `core/designsystem/Type.kt` to configure typography. Ensure line heights are accessible for both English and Tamil text rendering.
4. Modify `core/designsystem/Theme.kt` to export the `HouseOfGodTheme`.
5. Yield to human for build verification.

## Task 3: Home Feature - Verse of the Day
**Status:** DONE
**Instructions for Agent:**
1. Create `feature/home/HomeScreen.kt`.
2. Build the `VerseOfTheDayCard` Composable. Use a mock image URL with Coil, and apply a `Brush.verticalGradient` (transparent to 80% black) over the image to ensure text contrast.
3. Build placeholder cards for "Guided Reflection" and "Upcoming Event" using a `LazyColumn`.
4. Connect `HomeScreen` to the `AppNavHost`.
5. Yield to human for build verification.

## Task 4: Media Feature Skeleton
**Status:** DONE
**Instructions for Agent:**
1. Create `feature/media/MediaScreen.kt`.
2. Implement a top-level `TabRow` for two segments: [ Sermons ] and [ Music ].
3. Implement a simple state-driven switch to toggle between two `LazyVerticalGrid` or `LazyColumn` mock lists.
4. Use standard Material 3 list items with mock thumbnail URLs for the content.
5. Connect `MediaScreen` to the `AppNavHost`.
6. Yield to human for build verification.

## Task 5: Bible, Prayer, Profile Shells
**Status:** TODO
**Instructions for Agent:**
1. Create `feature/bible/BibleScreen.kt`, `feature/prayer/PrayerScreen.kt`, and `feature/profile/ProfileScreen.kt`.
2. Build basic UI shells for each matching the PRD scope (e.g., a simple "Submit Prayer Request" button in Prayer, a Settings list in Profile).
3. Connect all to the `AppNavHost`.
4. Yield to human for final Phase 0 verification.