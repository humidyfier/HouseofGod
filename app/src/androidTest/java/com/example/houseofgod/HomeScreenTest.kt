package com.example.houseofgod

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.houseofgod.core.designsystem.HouseOfGodTheme
import com.example.houseofgod.feature.home.HomeScreen
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_rendersAllCoreCards() {
        composeTestRule.setContent {
            HouseOfGodTheme {
                HomeScreen()
            }
        }

        // Header
        composeTestRule.onNodeWithText("HOUSE OF GOD").assertIsDisplayed()
        composeTestRule.onNodeWithText("Welcome to worship").assertIsDisplayed()

        // Verse of the Day
        composeTestRule.onNodeWithText("VERSE OF THE DAY").assertIsDisplayed()
        composeTestRule.onNodeWithText("John 3:16").assertIsDisplayed()
        composeTestRule.onNodeWithText("Read Chapter").assertIsDisplayed()

        // Guided Reflection
        composeTestRule.onNodeWithText("DAILY GUIDED REFLECTION").assertIsDisplayed()
        composeTestRule.onNodeWithText("Walking in Peace").assertIsDisplayed()
        composeTestRule.onNodeWithText("Begin Reflection").assertIsDisplayed()

        // Upcoming Event
        composeTestRule.onNodeWithText("UPCOMING CHURCH EVENT").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sunday Worship & Communion").assertIsDisplayed()
        composeTestRule.onNodeWithText("View Event Details").assertIsDisplayed()
    }

    @Test
    fun verseOfTheDay_readChapterClick_triggersCallback() {
        var clicked = false
        composeTestRule.setContent {
            HouseOfGodTheme {
                HomeScreen(
                    onReadChapterClick = { clicked = true }
                )
            }
        }

        composeTestRule.onNodeWithText("Read Chapter").performClick()
        assertTrue("onReadChapterClick should have been invoked", clicked)
    }
}
