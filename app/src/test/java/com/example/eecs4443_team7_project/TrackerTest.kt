package com.example.eecs4443_team7_project

import com.example.eecs4443_team7_project.models.Track
import com.example.eecs4443_team7_project.models.TrackCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class TrackerTest {
    @Test
    fun testTrackDataClass() {
        val track = Track("login", 123, TrackCategory.ACHIEVEMENT)
        assertEquals("login", track.key)
        assertEquals(123, track.label)
        assertEquals(TrackCategory.ACHIEVEMENT, track.category)
    }

    @Test
    fun testTrackCategoryEnums() {
        TrackCategory.entries.forEach { assertNotNull(it.displayName); assertTrue(it.iconRes > 0) }
    }
}

