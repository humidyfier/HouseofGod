package com.example.houseofgod

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.houseofgod.core.designsystem.HouseOfGodTheme
import com.example.houseofgod.feature.bible.BibleScreen
import com.example.houseofgod.feature.prayer.PrayerScreen
import com.example.houseofgod.feature.profile.ProfileScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FeatureShellsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bibleScreen_rendersReaderAndSwitchesLanguage() {
        composeTestRule.setContent {
            HouseOfGodTheme {
                BibleScreen()
            }
        }

        // Verify Header and Book/Chapter
        composeTestRule.onNodeWithText("John 3").assertIsDisplayed()
        composeTestRule.onNodeWithText("English").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tamil").assertIsDisplayed()

        // Verify initial English scripture
        composeTestRule.onNodeWithText("For God so loved the world, that he gave his only begotten Son, that whosoever believeth in him should not perish, but have everlasting life.").assertIsDisplayed()

        // Switch to Tamil
        composeTestRule.onNodeWithText("Tamil").performClick()
        composeTestRule.waitForIdle()

        // Verify Tamil scripture rendered with accessible line heights
        composeTestRule.onNodeWithText("யோவான் 3 ஆம் அதிகாரம்").assertIsDisplayed()
        composeTestRule.onNodeWithText("தேவன், தம்முடைய ஒரேபேறான குமாரனை விசுவாசிக்கிறவன் எவனோ அவன் கெட்டுப்போகாமல் நித்தியஜீவனை அடையும்படிக்கு, அவரைத் தந்தருளி, இவ்வளவாய் உலகத்தில் அன்புகூர்ந்தார்.").assertIsDisplayed()
    }

    @Test
    fun prayerScreen_rendersSubmissionFormAndHistory() {
        composeTestRule.setContent {
            HouseOfGodTheme {
                PrayerScreen()
            }
        }

        composeTestRule.onNodeWithText("Prayer Requests").assertIsDisplayed()
        composeTestRule.onNodeWithText("Submit a Prayer Request").assertIsDisplayed()
        composeTestRule.onNodeWithText("Healing").assertIsDisplayed()
        composeTestRule.onNodeWithText("Request Pastoral Callback").assertIsDisplayed()
        composeTestRule.onNodeWithText("Submit Anonymously (Prayer Team only)").assertIsDisplayed()
        composeTestRule.onNodeWithText("Submit Prayer Request").assertIsDisplayed()
        composeTestRule.onNodeWithText("My Prayer History").assertIsDisplayed()
    }

    @Test
    fun profileScreen_rendersChurchInfoAndSettings() {
        composeTestRule.setContent {
            HouseOfGodTheme {
                ProfileScreen()
            }
        }

        composeTestRule.onNodeWithText("Church Details & Gatherings").assertIsDisplayed()
        composeTestRule.onNodeWithText("Google Maps").assertIsDisplayed()
        composeTestRule.onNodeWithText("Contact Office").assertIsDisplayed()
        composeTestRule.onNodeWithText("App Settings").assertIsDisplayed()
        composeTestRule.onNodeWithText("Dark Theme").assertIsDisplayed()
        composeTestRule.onNodeWithText("Verse of the Day Notifications").assertIsDisplayed()
        composeTestRule.onNodeWithText("Clear My Prayer History").assertIsDisplayed()
        composeTestRule.onNodeWithText("Delete Account & Data").assertIsDisplayed()
    }
}
