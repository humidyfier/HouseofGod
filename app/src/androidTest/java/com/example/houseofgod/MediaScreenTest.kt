package com.example.houseofgod

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.houseofgod.core.designsystem.HouseOfGodTheme
import com.example.houseofgod.feature.media.MediaItem
import com.example.houseofgod.feature.media.MediaScreen
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MediaScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mediaScreen_rendersTabsAndInitialSermons() {
        composeTestRule.setContent {
            HouseOfGodTheme {
                MediaScreen()
            }
        }

        // Header
        composeTestRule.onNodeWithText("MEDIA & WORSHIP").assertIsDisplayed()
        composeTestRule.onNodeWithText("Watch & Listen").assertIsDisplayed()

        // Tabs
        composeTestRule.onNodeWithText("Sermons").assertIsDisplayed()
        composeTestRule.onNodeWithText("Music").assertIsDisplayed()

        // Initial tab is Sermons
        composeTestRule.onNodeWithText("The Power of Radical Faith").assertIsDisplayed()
        composeTestRule.onNodeWithText("Pastor John Victor").assertIsDisplayed()
    }

    @Test
    fun mediaScreen_togglingTabsSwitchesBetweenSermonsAndMusic() {
        composeTestRule.setContent {
            HouseOfGodTheme {
                MediaScreen()
            }
        }

        // Initially on Sermons
        composeTestRule.onNodeWithText("The Power of Radical Faith").assertIsDisplayed()

        // Switch to Music tab
        composeTestRule.onNodeWithText("Music").performClick()
        composeTestRule.waitForIdle()

        // Music items should be displayed
        composeTestRule.onNodeWithText("Way Maker / King of Kings (Live Worship)").assertIsDisplayed()
        composeTestRule.onNodeWithText("House of God Worship Bangalore").assertIsDisplayed()

        // Switch back to Sermons tab
        composeTestRule.onNodeWithText("Sermons").performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("The Power of Radical Faith").assertIsDisplayed()
    }

    @Test
    fun mediaScreen_clickingMediaItemTriggersCallback() {
        var clickedItem: MediaItem? = null
        composeTestRule.setContent {
            HouseOfGodTheme {
                MediaScreen(
                    onMediaItemClick = { clickedItem = it }
                )
            }
        }

        composeTestRule.onNodeWithText("The Power of Radical Faith").performClick()
        assertNotNull("Clicked item should not be null", clickedItem)
        assertEquals("sermon-1", clickedItem?.id)
    }
}
