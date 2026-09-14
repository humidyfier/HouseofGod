package com.example.houseofgod.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/**
 * Top-level destinations representing the primary navigation items in the bottom navigation bar.
 */
enum class TopLevelRoute(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    HOME(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    ),
    BIBLE(
        route = "bible",
        title = "Bible",
        icon = Icons.Default.MenuBook
    ),
    MEDIA(
        route = "media",
        title = "Media",
        icon = Icons.Default.PlayArrow
    ),
    PRAYER(
        route = "prayer",
        title = "Prayer",
        icon = Icons.Default.Favorite
    ),
    PROFILE(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    );

    companion object {
        val startDestination: String = HOME.route

        fun fromRoute(route: String?): TopLevelRoute? {
            return entries.firstOrNull { it.route == route }
        }
    }
}

/**
 * Material menu_book ImageVector definition (standard 24x24dp Material design vector).
 */
private var _menuBook: ImageVector? = null

val Icons.Default.MenuBook: ImageVector
    get() {
        if (_menuBook != null) {
            return _menuBook!!
        }
        _menuBook = ImageVector.Builder(
            name = "Filled.MenuBook",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(21.0f, 5.0f)
                curveToRelative(-1.11f, -0.35f, -2.33f, -0.5f, -3.5f, -0.5f)
                curveToRelative(-1.95f, 0.0f, -4.05f, 0.4f, -5.5f, 1.5f)
                curveToRelative(-1.45f, -1.1f, -3.55f, -1.5f, -5.5f, -1.5f)
                reflectiveCurveTo(2.45f, 4.9f, 1.0f, 6.0f)
                verticalLineToRelative(14.65f)
                curveToRelative(0.0f, 0.25f, 0.25f, 0.5f, 0.5f, 0.5f)
                curveToRelative(0.1f, 0.0f, 0.15f, -0.05f, 0.25f, -0.05f)
                curveTo(3.1f, 20.45f, 5.05f, 20.0f, 6.5f, 20.0f)
                curveToRelative(1.95f, 0.0f, 4.05f, 0.4f, 5.5f, 1.5f)
                curveToRelative(1.35f, -0.85f, 3.8f, -1.5f, 5.5f, -1.5f)
                curveToRelative(1.65f, 0.0f, 3.35f, 0.3f, 4.75f, 1.05f)
                curveToRelative(0.1f, 0.05f, 0.15f, 0.05f, 0.25f, 0.05f)
                curveToRelative(0.25f, 0.0f, 0.5f, -0.25f, 0.5f, -0.5f)
                verticalLineTo(6.0f)
                curveToRelative(-0.6f, -0.45f, -1.25f, -0.75f, -2.0f, -1.0f)
                close()
                moveTo(21.0f, 18.5f)
                curveToRelative(-1.1f, -0.35f, -2.3f, -0.5f, -3.5f, -0.5f)
                curveToRelative(-1.7f, 0.0f, -4.15f, 0.65f, -5.5f, 1.5f)
                verticalLineTo(8.0f)
                curveToRelative(1.35f, -0.85f, 3.8f, -1.5f, 5.5f, -1.5f)
                curveToRelative(1.2f, 0.0f, 2.4f, 0.15f, 3.5f, 0.5f)
                verticalLineToRelative(11.5f)
                close()
            }
        }.build()
        return _menuBook!!
    }
