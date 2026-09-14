package com.example.houseofgod.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.houseofgod.feature.bible.BibleScreen
import com.example.houseofgod.feature.home.HomeScreen
import com.example.houseofgod.feature.media.MediaScreen
import com.example.houseofgod.feature.prayer.PrayerScreen
import com.example.houseofgod.feature.profile.ProfileScreen

/**
 * Main application NavHost coordinating top-level navigation across the 5 core tabs.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = TopLevelRoute.startDestination
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = TopLevelRoute.HOME.route) {
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                onReadChapterClick = {
                    navController.navigate(TopLevelRoute.BIBLE.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = TopLevelRoute.BIBLE.route) {
            BibleScreen(modifier = Modifier.fillMaxSize())
        }
        composable(route = TopLevelRoute.MEDIA.route) {
            MediaScreen(modifier = Modifier.fillMaxSize())
        }
        composable(route = TopLevelRoute.PRAYER.route) {
            PrayerScreen(modifier = Modifier.fillMaxSize())
        }
        composable(route = TopLevelRoute.PROFILE.route) {
            ProfileScreen(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun ScreenPlaceholder(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
