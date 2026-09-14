# Domain Glossary (domain.md)

This document defines the ubiquitous language for the House of God application. Use these exact terms when naming variables, classes, and database columns to prevent ambiguity.

## Core Entities

### Daily Content
*   **Verse of the Day:** The primary daily scripture. It consists of a `verse_reference` (e.g., "John 3:16"), `verse_text`, and a `background_image_url`. Generated dynamically by the backend, never queried directly from external APIs by the client.
*   **Guided Reflection:** A short, optional devotional text (`reflection_title`, `reflection_body`) attached to the daily content.
*   **Guided Prayer:** A short, pre-written prayer attached to the daily content.
*   **Seasonal Campaign:** Backend-driven promotional content (e.g., Christmas banner) that overrides or supplements the daily dashboard.

### Media
*   **Sermon:** A teaching video item. Always hosted on YouTube. Requires `youtube_video_id`, `title`, `speaker`, and `published_at`.
*   **Worship Music:** A musical video item. Always hosted on YouTube. Requires `youtube_video_id`, `title`, `artist`, and `published_at`.
*   *Note:* The MVP does not use custom video hosting or raw MP4 files. All media plays via YouTube embedding or native intents.

### Prayer
*   **Prayer Request:** Sensitive, user-submitted pastoral care text.
*   **Contact Requested:** A boolean flag indicating the user wants a pastoral phone call. Requires a valid `phone_number` in their Profile.
*   **Anonymous Request:** A boolean flag indicating the request is for intercession only. The UI must hide the user's identity, and backend views must mask it from general admins.
*   **Status:** The lifecycle of a prayer request: `active`, `answered`, or `archived`.

### Bible
*   **Translation:** The version and language of the Bible text (e.g., `en-NIV`, `ta-BSI`). MVP supports English, Tamil, Kannada, and Hindi.
*   **Bookmark:** A user-saved reference to a specific Book, Chapter, and Verse.
*   **Reading Progress:** The last accessed chapter and scroll position, saved locally to allow the user to resume reading seamlessly.

### Users & Access
*   **Profile:** The extended user record tied to a Supabase Auth UUID. Contains `phone_number` and `preferred_language`.
*   **Role:** The permission level of the user (`member`, `prayer_team`, `editor`, `admin`). This drives Supabase Row Level Security (RLS) policies.