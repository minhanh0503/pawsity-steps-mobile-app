
package com.example.eecs4443_team7_project

import com.example.eecs4443_team7_project.models.User
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * NOTE: User object is not used in the app, as all user-related data is handled via SharedPreferences.
 * This test is for archival and completeness only.
 */
class UserTest {
    @Test
    fun testUserDefault() {
        val user = User()
        assertEquals("", user.id)
        assertEquals("", user.name)
        assertEquals("", user.email)
        assertTrue(user.preferences.isEmpty())
    }

    @Test
    fun testUserCustomFields() {
        val prefs = mapOf("theme" to "dark", "volume" to "medium")
        val user = User(
            id = "u123",
            name = "Alice",
            email = "alice@example.com",
            preferences = prefs
        )
        assertEquals("u123", user.id)
        assertEquals("Alice", user.name)
        assertEquals("alice@example.com", user.email)
        assertEquals(prefs, user.preferences)
    }
}

