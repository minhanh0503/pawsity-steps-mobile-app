package com.example.eecs4443_team7_project

import com.example.eecs4443_team7_project.models.Quest
import com.example.eecs4443_team7_project.models.QuestCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class QuestTest {
    @Test
    fun testQuestDataClass() {
        val quest = Quest("q1", "Title", "Desc", 10, QuestCategory.PHYSICAL, false)
        assertEquals("q1", quest.id)
        assertEquals("Title", quest.title)
        assertEquals("Desc", quest.description)
        assertEquals(10, quest.points)
        assertEquals(QuestCategory.PHYSICAL, quest.category)
        assertFalse(quest.isCompleted)
    }

    @Test
    fun testQuestCategoryEnums() {
        QuestCategory.entries.forEach { assertNotNull(it.displayName); assertTrue(it.iconRes > 0) }
    }
}

