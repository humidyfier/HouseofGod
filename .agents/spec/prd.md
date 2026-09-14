# Product Requirements Document (PRD)
**Project:** House of God Church, Bangalore - Mobile App
**Platform:** Android (Jetpack Compose)
**Phase:** MVP (Minimum Viable Product)

## 1. Product Vision & Objective
Create a beautiful, trustworthy, calm, and modern Christian church app that serves as a daily spiritual companion. The app bridges the gap between a Sunday-only experience and daily engagement through Bible reading, Verse of the Day, guided reflection, prayer, sermons, and worship music.

## 2. Target Audience & Languages
*   **Initial Audience:** 100–150 church members and attendees in Bangalore.
*   **Future Audience:** Christians outside Bangalore and the global diaspora.
*   **Supported Languages:** English, Tamil, Kannada, Malayalam, and Hindi.

## 3. Core User Journeys (CUJs)
1.  **The Daily Devotional:** User opens the app, reads the Verse of the Day, completes a guided reflection, and marks a short prayer as complete.
2.  **Immersive Scripture Reading:** User navigates to a specific Bible chapter, switches between supported languages, adjusts the font size for readability, and bookmarks a verse.
3.  **Media Consumption:** User browses recent Sunday sermons or worship sessions and plays a YouTube video directly within the app without being kicked out to a browser.
4.  **Pastoral Care Request:** User submits a prayer request, optionally requesting a pastoral callback (which validates their profile phone number), or submits it for anonymous intercession.

## 4. MVP Feature Scope

### 4.0 Authentication
*   **Methods:** Google Sign-In and Email/Password.
*   **Requirement:** Users can browse public church information and public content, but must authenticate to save bookmarks, sync reading progress, and submit non-anonymous prayer requests.

### 4.1 Home (The Spiritual Dashboard)
*   **Verse of the Day:** Premium hero card with gradient/texture, verse text, and a "Read Chapter" deep-link.
*   **Guided Content:** Short daily reflection and guided prayer cards.
*   **Dynamic Campaigns:** Backend-driven seasonal banners (e.g., Christmas, Easter) that render natively without requiring an app update.
*   **Highlights:** Single card for the newest media item and one upcoming church event.

### 4.2 Bible
*   **Data Source:** Live integration with a reliable public Bible API from Day 1.
*   **Languages:** English, Tamil, Kannada, and Hindi.
*   **Reader UX:** A premium, distraction-free interface closely mirroring the YouVersion app. Includes book/chapter selection, clean typography, dark/light mode support, and font-size controls.
*   **Interactions:** Tap/long-press to bookmark, copy, or share a verse.
*   **Persistence:** Save scroll position and last-read verse locally.

### 4.3 Media
*   **Structure:** Segmented tabs for [ Sermons ] and [ Music ].
*   **Playback:** In-app YouTube video playback using an embedded YouTube player library to maintain app context.
*   **Metadata:** Driven by Supabase backend (title, date, speaker/artist, scripture reference, thumbnail URL).

### 4.4 Prayer
*   **Submission Flow:** Form to enter a prayer category and text.
*   **Contact Logic:**
    *   **"Contact Me" Checkbox:** User requests a callback. The app checks the user's profile for a phone number. If missing, the app intercepts the submission and prompts the user to add their phone number in the Profile section.
    *   **"Anonymous / Prayer Only" Option:** The request is routed to the prayer team for intercession only; no phone call is triggered, and identity is shielded from general admin views.
*   **History:** Authenticated users can view a private list of their past requests and mark them as answered.

### 4.5 Profile & Church Info
*   **User Preferences:** App theme (Dark/Light/System), preferred language, and notification toggles.
*   **Church Details:** Service times, Bangalore location with Google Maps deep-link, and contact info (WhatsApp, Email).
*   **Data Management:** Clear options to delete prayer history and delete the account entirely.

## 5. Non-Functional Requirements (NFRs)
*   **Performance:** UI must render smoothly on mid-range Android devices common in India. Use pagination for media lists and caching for daily text.
*   **Offline Support:** Cache Home dashboard content, basic church details, and user preferences using local persistence (DataStore/Room).
*   **Privacy & Security:** Prayer data is strictly treated as sensitive pastoral care data. Protected via Supabase Row Level Security (RLS). No analytics tracking on prayer text.

## 6. Future Scope (Phase 2+)
*   iOS platform support (Kotlin Multiplatform).
*   Online giving and recurring donations (Razorpay).
*   Full-text Bible search.
*   Dedicated Next.js web portal for church administrators.
*   Custom audio player for MP3 sermons.