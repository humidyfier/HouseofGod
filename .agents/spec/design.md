# System Design & Architecture (design.md)
**Project:** House of God Church, Bangalore - Mobile App
**Backend:** Supabase (PostgreSQL, Auth, RLS)
**Client:** Android (Jetpack Compose, Kotlin)

## 1. System Architecture
The client application follows a strict Single-Activity, Feature-Driven MVVM architecture with Unidirectional Data Flow (UDF).
*   **UI Layer:** Jetpack Compose screens observing `StateFlow<UiState>` from ViewModels.
*   **Domain Layer:** UseCases (optional for simple CRUD) and Repository interfaces.
*   **Data Layer:** Repository implementations using the Supabase Kotlin Client for remote data and Room/DataStore for local caching.
*   **Dependency Injection:** Dagger Hilt.

## 2. Database Schema (Supabase PostgreSQL)
The backend utilizes a normalized relational structure. UUIDs are used for primary keys to prevent enumeration.

### `profiles`
Stores extended user information linked to Supabase Auth.
*   `id` (UUID, Primary Key, References `auth.users`)
*   `full_name` (Text, Nullable)
*   `phone_number` (Text, Nullable) - *Required if a user requests a pastoral callback.*
*   `preferred_language` (Text, Default 'en') - *Enum: en, ta, kn, hi*
*   `role` (Text, Default 'member') - *Enum: member, prayer_team, editor, admin*
*   `created_at` (Timestamp)
*   `updated_at` (Timestamp)

### `daily_content`
Drives the Home screen dashboard.
*   `id` (UUID, Primary Key)
*   `content_date` (Date)
*   `language` (Text)
*   `verse_reference` (Text)
*   `verse_text` (Text)
*   `reflection_title` (Text, Nullable)
*   `reflection_body` (Text, Nullable)
*   `guided_prayer_body` (Text, Nullable)
*   `is_published` (Boolean, Default false)

### `media_items`
Stores metadata for Sermons and Worship Music.
*   `id` (UUID, Primary Key)
*   `type` (Text) - *Enum: sermon, music*
*   `title` (Text)
*   `title_local` (Text, Nullable) - *For Tamil/Kannada/Hindi titles*
*   `speaker_or_artist` (Text)
*   `youtube_video_id` (Text) - *Extracted ID, not the full URL, for reliable player embedding*
*   `thumbnail_url` (Text, Nullable)
*   `published_at` (Timestamp)

### `prayer_requests`
Sensitive pastoral care data.
*   `id` (UUID, Primary Key)
*   `user_id` (UUID, References `profiles.id`)
*   `category` (Text)
*   `request_text` (Text)
*   `contact_requested` (Boolean) - *If true, frontend must validate `profiles.phone_number` is not null.*
*   `is_anonymous` (Boolean) - *If true, frontend masks identity; backend hides `user_id` from standard admin views.*
*   `status` (Text, Default 'active') - *Enum: active, answered, archived*
*   `created_at` (Timestamp)

### `bible_bookmarks`
*   `id` (UUID, Primary Key)
*   `user_id` (UUID, References `profiles.id`)
*   `translation_id` (Text)
*   `book_id` (Text)
*   `chapter_number` (Integer)
*   `verse_number` (Integer)
*   `created_at` (Timestamp)

## 3. Security & Row Level Security (RLS)
Supabase RLS policies are strictly enforced at the database level to prevent client-side manipulation.

*   **Public Data:** `daily_content`, `media_items`, and `events` are readable by anon/authenticated users, but only writable by users with the `editor` or `admin` role.
*   **Profiles:** Users can only `SELECT` and `UPDATE` their own profile row.
*   **Prayer Requests (Strict):**
    *   `INSERT`: Authenticated users only.
    *   `SELECT`/`UPDATE`/`DELETE`: Users can only access rows where `user_id == auth.uid()`.
    *   `SELECT` (Prayer Team): Users with `role == 'prayer_team'` can read all active requests, but UI/API must respect the `is_anonymous` flag.

## 4. External API Integrations
*   **Bible API:** Integration with a public Bible API (e.g., Free Bibles API or similar REST endpoint) to fetch English, Tamil, Kannada, and Hindi texts. Requires local caching of fetched chapters to minimize network load.
*   **YouTube Player:** Use the official Android YouTube Player API or a vetted wrapper (like `pierfrancescosoffritti/android-youtube-player`) to embed video playback securely within the Compose hierarchy.

## 5. Automated Daily Content Orchestration
*   **Verse Generation:** A Supabase Edge Function (Cron) runs daily to fetch the Verse of the Day from an external API, construct the daily payload, and insert it into the `daily_content` table. The Android client strictly reads from this table and never calls external Verse APIs directly.
*   **Hero Images:** Background images for the Verse of the Day are hosted in a Supabase Storage bucket. The Edge Function assigns a daily image URL to the database record.
*   **UI Rendering:** The Android app loads the remote image using Coil and applies a dark Compose `Brush.verticalGradient` overlay to guarantee text contrast and readability.