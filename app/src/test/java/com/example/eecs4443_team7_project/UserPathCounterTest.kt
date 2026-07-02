package com.example.eecs4443_team7_project

import com.example.eecs4443_team7_project.util.UserPathCounter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserPathCounterTest {
    @Before
    fun resetBefore() {
        UserPathCounter.reset()
    }

    @Test
    fun testOutOfBoundsIgnored() {
        UserPathCounter.logUserPathEvent(0)
        UserPathCounter.logUserPathEvent(5)
        val counts = UserPathCounter.getCounts()
        assertEquals(listOf(0,0,0,0), counts)
    }
}

