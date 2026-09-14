package com.example.houseofgod

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun bottomBar_rendersAllFiveDestinations() {
        composeTestRule.onNodeWithContentDescription("Home").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Bible").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Media").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Prayer").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Profile").assertIsDisplayed()
    }

    @Test
    fun startDestination_isHomeScreen() {
        composeTestRule.onNodeWithText("Welcome to worship").assertIsDisplayed()
    }

    @Test
    fun navigation_clickingTabsNavigatesToExpectedScreens() {
        // Navigate to Bible
        composeTestRule.onNodeWithContentDescription("Bible").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("John 3 (KJV / BSI)").assertIsDisplayed()

        // Navigate to Media
        composeTestRule.onNodeWithContentDescription("Media").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Watch & Listen").assertIsDisplayed()

        // Navigate to Prayer
        composeTestRule.onNodeWithContentDescription("Prayer").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Prayer Requests").assertIsDisplayed()

        // Navigate to Profile
        composeTestRule.onNodeWithContentDescription("Profile").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Church Details & Gatherings").assertIsDisplayed()

        // Navigate back to Home
        composeTestRule.onNodeWithContentDescription("Home").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Welcome to worship").assertIsDisplayed()
    }
}
