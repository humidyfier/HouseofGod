# Application Architecture (architecture.md)

## 1. Core Paradigm
This application strictly follows a **Single-Activity, Feature-Driven MVVM** architecture using **Unidirectional Data Flow (UDF)**.
- The UI strictly observes state and emits events.
- The ViewModel processes events, mutates state, and exposes a single source of truth.
- The Data Layer abstracts the origin of data (network vs. local cache).

## 2. State Management (UDF)
Every feature screen must define a `UiState` data class representing its entire view state.
- ViewModels must hold state in a `MutableStateFlow` and expose it as an immutable `StateFlow<UiState>`.
- Composables must consume state using `collectAsStateWithLifecycle()` to prevent background resource leaks.
- UI actions (button clicks, text input) are passed to the ViewModel via specific intent functions (e.g., `fun onBookmarkClicked(verseId: String)`).

## 3. UI Layer (Jetpack Compose)
- **No XML:** UI is 100% Jetpack Compose.
- **Statelessness:** Screen-level Composables receive state and hoisted event lambdas. Keep business logic out of the UI tree.
- **Modifiers:** Every reusable Composable must accept a `modifier: Modifier = Modifier` as its first optional parameter to allow the caller to dictate sizing and layout.
- **Theme:** Material 3 baseline, strictly adhering to the `HouseOfGodTheme` dark-first design system.

## 4. Navigation
- **Library:** Navigation Compose.
- **Structure:** `AppNavHost` manages top-level routing (Home, Bible, Media, Prayer, Profile) via a `Scaffold` and `BottomAppBar`.
- **Arguments:** Pass minimal primitives (like IDs) via navigation routes. Fetch full objects from the Repository in the destination's ViewModel.

## 5. Data & Domain Layer
- **Repositories:** All data access goes through Repository interfaces. ViewModels never directly call Supabase or Room.
- **Offline-First Strategy:** For Bible progress, bookmarks, and daily content, the Repository should fetch from the local Room database/DataStore first, then refresh from the Supabase remote client in the background if network is available.
- **Dependency Injection:** Dagger Hilt is used to inject Repositories into ViewModels and singletons into the application graph.

## 6. Concurrency
- **Coroutines:** Use Kotlin Coroutines for all asynchronous work. No RxJava or Threads.
- **Dispatchers:** ViewModels execute on `Dispatchers.Main` by default. Repositories must shift heavy database or network work to `Dispatchers.IO`.