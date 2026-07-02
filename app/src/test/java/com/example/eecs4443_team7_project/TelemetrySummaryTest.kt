package com.example.eecs4443_team7_project

import com.example.eecs4443_team7_project.models.TelemetrySummary
import org.junit.Assert.assertEquals
import org.junit.Test

class TelemetrySummaryTest {
    @Test
    fun testTelemetrySummaryInit() {
        val summary = TelemetrySummary(
            participantName = "P1",
            uiType = "A",
            timePerTask = listOf(1.0, 2.0, 3.0, 4.0),
            errorRatePerTask = listOf(0, 1, 2, 3),
            batteryPerTask = listOf(100, 99, 98, 97)
        )
        assertEquals("P1", summary.participantName)
        assertEquals("A", summary.uiType)
        assertEquals(listOf(1.0,2.0,3.0,4.0), summary.timePerTask)
        assertEquals(listOf(0,1,2,3), summary.errorRatePerTask)
        assertEquals(listOf(100,99,98,97), summary.batteryPerTask)
    }
}

