package com.example.houseofgod.core.designsystem

import androidx.compose.ui.graphics.Color

// ---------------------------------------------------------------------------
// House of God Design System - Dark-first, calm, premium devotional palette
// ---------------------------------------------------------------------------

// Background & Surfaces (Deep charcoal & midnight tones, calming and glare-free)
val MidnightCharcoal = Color(0xFF0F1115)       // Root screen background
val DarkElevatedSurface = Color(0xFF181A20)    // Cards, sheets, navigation bars
val DarkSurfaceVariant = Color(0xFF222630)     // Inner cards, text inputs, chips
val DarkSurfaceHighlight = Color(0xFF2C313E)   // Elevated dialogs, stroke accents

// Primary Brand Accent - Radiant Warm Gold (Sanctuary warmth, cathedral light)
val RadiantGold = Color(0xFFE5B869)            // Primary accent
val RadiantGoldDark = Color(0xFF221703)        // Text/icon on primary
val RadiantGoldContainer = Color(0xFF3B2E15)   // Subtle gold tinted container
val RadiantGoldContainerText = Color(0xFFF7E6C4)// Text on primary container

// Secondary Accent - Serene Slate / Sky (Peace, calm prayer)
val SanctuarySlate = Color(0xFFA8C5DA)
val SanctuarySlateDark = Color(0xFF122837)
val SanctuarySlateContainer = Color(0xFF253B4B)
val SanctuarySlateContainerText = Color(0xFFD4E7F5)

// Tertiary Accent - Candlelight Warm Amber (Devotional warmth)
val CandlelightAmber = Color(0xFFDF9B78)
val CandlelightAmberDark = Color(0xFF401D0C)
val CandlelightAmberContainer = Color(0xFF5A2E19)
val CandlelightAmberContainerText = Color(0xFFFFDBCF)

// High-Readability Text & Outlines
val TextHighContrast = Color(0xFFF0F1F5)       // Primary readable text on dark
val TextMediumContrast = Color(0xFFB5BAC9)     // Secondary / supporting text
val TextSubtle = Color(0xFF7F8596)             // Captions, inactive icons
val DarkOutline = Color(0xFF474C5B)            // Card outlines, dividers
val DarkOutlineVariant = Color(0xFF2E323E)     // Subtle dividers

// Feedback / Error
val ErrorRed = Color(0xFFFFB4AB)
val OnErrorRed = Color(0xFF690005)
val ErrorRedContainer = Color(0xFF93000A)
val OnErrorRedContainer = Color(0xFFFFDAD6)

// Fallback Light Palette (For daylight / accessibility preferences)
val LightBackground = Color(0xFFFCFCFF)
val LightSurface = Color(0xFFF6F7FB)
val LightSurfaceVariant = Color(0xFFE5E7EF)
val LightPrimary = Color(0xFF7D5700)
val LightOnPrimary = Color(0xFFFFFFFF)
val LightPrimaryContainer = Color(0xFFFFDEA3)
val LightOnPrimaryContainer = Color(0xFF271900)
val LightTextHigh = Color(0xFF191C20)
val LightTextMedium = Color(0xFF43474F)
