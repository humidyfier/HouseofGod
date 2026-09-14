package com.example.houseofgod.core.designsystem

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val HouseOfGodDarkColorScheme = darkColorScheme(
    primary = RadiantGold,
    onPrimary = RadiantGoldDark,
    primaryContainer = RadiantGoldContainer,
    onPrimaryContainer = RadiantGoldContainerText,
    secondary = SanctuarySlate,
    onSecondary = SanctuarySlateDark,
    secondaryContainer = SanctuarySlateContainer,
    onSecondaryContainer = SanctuarySlateContainerText,
    tertiary = CandlelightAmber,
    onTertiary = CandlelightAmberDark,
    tertiaryContainer = CandlelightAmberContainer,
    onTertiaryContainer = CandlelightAmberContainerText,
    background = MidnightCharcoal,
    onBackground = TextHighContrast,
    surface = DarkElevatedSurface,
    onSurface = TextHighContrast,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = TextMediumContrast,
    surfaceContainer = DarkElevatedSurface,
    surfaceContainerHigh = DarkSurfaceVariant,
    surfaceContainerHighest = DarkSurfaceHighlight,
    outline = DarkOutline,
    outlineVariant = DarkOutlineVariant,
    error = ErrorRed,
    onError = OnErrorRed,
    errorContainer = ErrorRedContainer,
    onErrorContainer = OnErrorRedContainer
)

val HouseOfGodLightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightPrimaryContainer,
    onPrimaryContainer = LightOnPrimaryContainer,
    secondary = SanctuarySlateDark,
    onSecondary = LightBackground,
    background = LightBackground,
    onBackground = LightTextHigh,
    surface = LightSurface,
    onSurface = LightTextHigh,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightTextMedium,
    error = ErrorRed,
    onError = OnErrorRed
)

/**
 * House of God primary theme.
 * Dark-first design by default to foster a reverent, calm, glare-free spiritual environment.
 */
@Composable
fun HouseOfGodTheme(
    darkTheme: Boolean = true, // Default to true: dark-first design
    dynamicColor: Boolean = false, // Default to false: preserve signature gold & midnight brand palette
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> HouseOfGodDarkColorScheme
        else -> HouseOfGodLightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = HouseOfGodTypography,
        content = content
    )
}
