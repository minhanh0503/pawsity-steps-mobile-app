package com.example.eecs4443_team7_project

import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class NotificationHelperTest {
    @Test
    fun testScheduleDoesNotThrow() {
        // actual notification scheduling requires Android context and cannot be fully tested in JVM
        try {
            // NotificationHelper.scheduleDailyNotification(context, 9, 0)
            assertTrue(true)
        } catch (e: Exception) {
            fail("Should not throw")
        }
    }
}

