package com.example.eecs4443_team7_project

import com.example.eecs4443_team7_project.util.SoundManager
import org.junit.Assert.assertTrue
import org.junit.Test

class SoundManagerTest {
    @Test
    fun testReleaseDoesNotThrow() {
        // Should not throw even if not initialized
        SoundManager.release()
        assertTrue(true)
    }
}

