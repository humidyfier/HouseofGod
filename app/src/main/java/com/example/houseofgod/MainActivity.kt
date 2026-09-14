
package com.example.houseofgod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.houseofgod.core.designsystem.HouseOfGodTheme
import com.example.houseofgod.core.designsystem.RadiantGold
import com.example.houseofgod.core.designsystem.TextMediumContrast
import com.example.houseofgod.core.navigation.AppNavHost
import com.example.houseofgod.core.navigation.TopLevelRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HouseOfGodTheme {
                MainAppScaffold()
            }
        }
    }
}

@Composable
fun MainAppScaffold(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val density = LocalDensity.current
    val barHeightPx = with(density) { 140.dp.toPx() }

    var isBottomBarVisible by rememberSaveable { mutableStateOf(true) }

    // Intercept scroll deltas to trigger scroll-to-hide behavior
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            private var accumulatedDelta = 0f

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                accumulatedDelta += delta
                if (accumulatedDelta < -15f) {
                    // Scrolling down -> hide bar
                    isBottomBarVisible = false
                    accumulatedDelta = 0f
                } else if (accumulatedDelta > 15f) {
                    // Scrolling up -> reveal bar
                    isBottomBarVisible = true
                    accumulatedDelta = 0f
                }
                return Offset.Zero
            }
        }
    }

    val translationY by animateFloatAsState(
        targetValue = if (isBottomBarVisible) 0f else barHeightPx,
        animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing),
        label = "BottomBarTranslationY"
    )

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection),
        containerColor = Color.Transparent,
        bottomBar = {
            FloatingGlassBottomBar(
                navController = navController,
                translationY = translationY
            )
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        )
    }
}

/**
 * Custom floating pill-shaped bottom bar with translucent dark glassmorphism styling
 * and a soft glowing indicator for the selected tab.
 */
@Composable
fun FloatingGlassBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    translationY: Float = 0f
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val routes = TopLevelRoute.entries
    val selectedIndex = routes.indexOfFirst { topLevelRoute ->
        currentDestination?.hierarchy?.any { it.route == topLevelRoute.route } == true
    }.coerceAtLeast(0)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                this.translationY = translationY
            }
            .navigationBarsPadding()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = RoundedCornerShape(50),
            color = Color(0xFF10131B).copy(alpha = 0.96f), // High opacity (96%) dark glass to prevent background bleed-through
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.12f)), // Crisp glass refraction rim
            shadowElevation = 20.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                val tabCount = routes.size
                val tabWidth = maxWidth / tabCount
                val pillWidth = tabWidth * 1.10f // Elongated 10% horizontally
                val pillHorizontalOffset = (pillWidth - tabWidth) / 2f

                // Smooth and noticeable sliding transition across sections (420ms)
                val animatedIndex by animateFloatAsState(
                    targetValue = selectedIndex.toFloat(),
                    animationSpec = tween(
                        durationMillis = 420,
                        easing = FastOutSlowInEasing
                    ),
                    label = "SlidingYellowPillIndicator"
                )

                // 1. Sliding yellow button with rounded edges (pill shape), elongated 10% horizontally
                Box(
                    modifier = Modifier
                        .offset(x = (tabWidth * animatedIndex) - pillHorizontalOffset)
                        .width(pillWidth)
                        .fillMaxHeight()
                        .background(
                            color = RadiantGold.copy(alpha = 0.20f),
                            shape = RoundedCornerShape(50)
                        )
                        .border(
                            border = BorderStroke(1.dp, RadiantGold.copy(alpha = 0.45f)),
                            shape = RoundedCornerShape(50)
                        )
                )

                // 2. Tab Items Row placed on top of the sliding indicator
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    routes.forEachIndexed { index, topLevelRoute ->
                        val isSelected = index == selectedIndex

                        FloatingBarItem(
                            route = topLevelRoute,
                            isSelected = isSelected,
                            onClick = {
                                navController.navigate(topLevelRoute.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.FloatingBarItem(
    route: TopLevelRoute,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedColor by animateColorAsState(
        targetValue = if (isSelected) RadiantGold else TextMediumContrast,
        animationSpec = tween(300),
        label = "BarItemColor"
    )

    Box(
        modifier = modifier
            .weight(1f)
            .fillMaxHeight()
            .clip(RoundedCornerShape(50))
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = route.icon,
                contentDescription = route.title,
                tint = animatedColor,
                modifier = Modifier.size(22.dp)
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = route.title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    letterSpacing = 0.3.sp
                ),
                color = animatedColor,
                maxLines = 1
            )
        }
    }
}

/**
 * Backward-compatible alias for existing navigation scaffolding.
 */
@Composable
fun HouseOfGodBottomAppBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    FloatingGlassBottomBar(
        navController = navController,
        modifier = modifier
    )
}