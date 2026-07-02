package com.example.eecs4443_team7_project

import com.example.eecs4443_team7_project.models.Affirmations
import org.junit.Assert.assertTrue
import org.junit.Test

class AffirmationsTest {
    @Test
    fun testAffirmationsListNotEmpty() {
        assertTrue(Affirmations.list.isNotEmpty())
    }

    @Test
    fun testAffirmationsContent() {
        assertTrue(Affirmations.list.contains("I am capable of achieving my goals."))
    }
}

