package com.example.houseofgod

import androidx.compose.ui.unit.sp
import com.example.houseofgod.core.designsystem.DarkElevatedSurface
import com.example.houseofgod.core.designsystem.HouseOfGodDarkColorScheme
import com.example.houseofgod.core.designsystem.HouseOfGodTypography
import com.example.houseofgod.core.designsystem.MidnightCharcoal
import com.example.houseofgod.core.designsystem.RadiantGold
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DesignSystemTest {

    @Test
    fun verifyDarkFirstColorScheme() {
        assertEquals(MidnightCharcoal, HouseOfGodDarkColorScheme.background)
        assertEquals(DarkElevatedSurface, HouseOfGodDarkColorScheme.surface)
        assertEquals(RadiantGold, HouseOfGodDarkColorScheme.primary)
    }

    @Test
    fun verifyAccessibleLineHeightsForTamilAndEnglishTypography() {
        // Tamil and complex Indic scripts require extra vertical space to prevent clipping
        assertTrue(
            "bodyLarge line height must be >= 28sp for Tamil vowel marks",
            HouseOfGodTypography.bodyLarge.lineHeight >= 28.sp
        )
        assertTrue(
            "bodyMedium line height must be >= 24sp for Tamil scripture reading",
            HouseOfGodTypography.bodyMedium.lineHeight >= 24.sp
        )
        assertTrue(
            "headlineLarge line height must be >= 40sp for Tamil headers",
            HouseOfGodTypography.headlineLarge.lineHeight >= 40.sp
        )
    }
}
