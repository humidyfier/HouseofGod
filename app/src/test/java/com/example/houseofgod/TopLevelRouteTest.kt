package com.example.houseofgod

import com.example.houseofgod.core.navigation.TopLevelRoute
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class TopLevelRouteTest {

    @Test
    fun verifyFiveRoutesExist() {
        val routes = TopLevelRoute.entries
        assertEquals(5, routes.size)
    }

    @Test
    fun verifyStartDestinationIsHome() {
        assertEquals("home", TopLevelRoute.startDestination)
        assertEquals(TopLevelRoute.HOME.route, TopLevelRoute.startDestination)
    }

    @Test
    fun verifyRouteResolution() {
        assertEquals(TopLevelRoute.HOME, TopLevelRoute.fromRoute("home"))
        assertEquals(TopLevelRoute.BIBLE, TopLevelRoute.fromRoute("bible"))
        assertEquals(TopLevelRoute.MEDIA, TopLevelRoute.fromRoute("media"))
        assertEquals(TopLevelRoute.PRAYER, TopLevelRoute.fromRoute("prayer"))
        assertEquals(TopLevelRoute.PROFILE, TopLevelRoute.fromRoute("profile"))
        assertNull(TopLevelRoute.fromRoute("unknown"))
        assertNull(TopLevelRoute.fromRoute(null))
    }

    @Test
    fun verifyIconsAndTitlesConfigured() {
        TopLevelRoute.entries.forEach { route ->
            assertNotNull(route.icon)
            assertNotNull(route.title)
            assertNotNull(route.route)
        }
    }
}
