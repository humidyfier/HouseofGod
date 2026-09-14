package com.example.houseofgod

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
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
        composeTestRule.onNodeWithText("Home").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bible").assertIsDisplayed()
        composeTestRule.onNodeWithText("Media").assertIsDisplayed()
        composeTestRule.onNodeWithText("Prayer").assertIsDisplayed()
        composeTestRule.onNodeWithText("Profile").assertIsDisplayed()
    }

    @Test
    fun startDestination_isHomeScreen() {
        composeTestRule.onNodeWithText("Welcome to worship").assertIsDisplayed()
    }

    @Test
    fun navigation_clickingTabsNavigatesToExpectedScreens() {
        // Navigate to Bible
        composeTestRule.onNodeWithText("Bible").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Bible Screen").assertIsDisplayed()

        // Navigate to Media
        composeTestRule.onNodeWithText("Media").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Media Screen").assertIsDisplayed()

        // Navigate to Prayer
        composeTestRule.onNodeWithText("Prayer").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Prayer Screen").assertIsDisplayed()

        // Navigate to Profile
        composeTestRule.onNodeWithText("Profile").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Profile Screen").assertIsDisplayed()

        // Navigate back to Home
        composeTestRule.onNodeWithText("Home").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Welcome to worship").assertIsDisplayed()
    }
}
