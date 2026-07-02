package com.example.eecs4443_team7_project

import com.example.eecs4443_team7_project.ui.navigation.NavigationOption
import org.junit.Assert.assertTrue
import org.junit.Test

class NavigationOptionTest {
    @Test
    fun testNavigationOptionEnums() {
        val options = NavigationOption.entries.toTypedArray()
        assertTrue(options.contains(NavigationOption.BOTTOM_BAR))
        assertTrue(options.contains(NavigationOption.HAMBURGER))
        assertTrue(options.contains(NavigationOption.HYBRID))
    }
}

