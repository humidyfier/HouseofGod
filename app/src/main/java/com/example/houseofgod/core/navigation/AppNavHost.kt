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
import com.example.houseofgod.feature.home.HomeScreen

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
            BibleScreenPlaceholder(modifier = Modifier.fillMaxSize())
        }
        composable(route = TopLevelRoute.MEDIA.route) {
            MediaScreenPlaceholder(modifier = Modifier.fillMaxSize())
        }
        composable(route = TopLevelRoute.PRAYER.route) {
            PrayerScreenPlaceholder(modifier = Modifier.fillMaxSize())
        }
        composable(route = TopLevelRoute.PROFILE.route) {
            ProfileScreenPlaceholder(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun HomeScreenPlaceholder(
    modifier: Modifier = Modifier
) {
    ScreenPlaceholder(
        title = "Home Screen",
        modifier = modifier
    )
}

@Composable
fun BibleScreenPlaceholder(
    modifier: Modifier = Modifier
) {
    ScreenPlaceholder(
        title = "Bible Screen",
        modifier = modifier
    )
}

@Composable
fun MediaScreenPlaceholder(
    modifier: Modifier = Modifier
) {
    ScreenPlaceholder(
        title = "Media Screen",
        modifier = modifier
    )
}

@Composable
fun PrayerScreenPlaceholder(
    modifier: Modifier = Modifier
) {
    ScreenPlaceholder(
        title = "Prayer Screen",
        modifier = modifier
    )
}

@Composable
fun ProfileScreenPlaceholder(
    modifier: Modifier = Modifier
) {
    ScreenPlaceholder(
        title = "Profile Screen",
        modifier = modifier
    )
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
