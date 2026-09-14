
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.houseofgod.core.designsystem.TextSubtle
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
            color = Color(0xFF13161F).copy(alpha = 0.82f), // Translucent dark liquid glass
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f)), // Subtle glass refraction rim
            shadowElevation = 16.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TopLevelRoute.entries.forEach { topLevelRoute ->
                    val isSelected = currentDestination?.hierarchy?.any { it.route == topLevelRoute.route } == true

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

@Composable
fun RowScope.FloatingBarItem(
    route: TopLevelRoute,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedColor by animateColorAsState(
        targetValue = if (isSelected) RadiantGold else TextSubtle,
        animationSpec = tween(300),
        label = "BarItemColor"
    )

    val pillBackground = if (isSelected) {
        RadiantGold.copy(alpha = 0.16f)
    } else {
        Color.Transparent
    }

    val pillBorder = if (isSelected) {
        BorderStroke(1.dp, RadiantGold.copy(alpha = 0.35f))
    } else {
        null
    }

    Box(
        modifier = modifier
            .weight(1f)
            .clip(RoundedCornerShape(50))
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .background(pillBackground, shape = RoundedCornerShape(50))
            .then(
                if (pillBorder != null) Modifier.border(pillBorder, shape = RoundedCornerShape(50))
                else Modifier
            )
            .padding(vertical = 10.dp),
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