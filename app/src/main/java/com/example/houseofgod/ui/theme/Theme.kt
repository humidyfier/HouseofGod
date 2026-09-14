package com.example.houseofgod.ui.theme

import androidx.compose.runtime.Composable
import com.example.houseofgod.core.designsystem.HouseOfGodTheme

/**
 * Backward-compatible alias for HouseofGodTheme pointing to the core design system.
 */
@Composable
fun HouseofGodTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    HouseOfGodTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor,
        content = content
    )
}