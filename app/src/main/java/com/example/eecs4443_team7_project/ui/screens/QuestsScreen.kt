package com.example.eecs4443_team7_project.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import com.example.eecs4443_team7_project.R
import com.example.eecs4443_team7_project.models.Quest
import com.example.eecs4443_team7_project.models.QuestCategory
import com.example.eecs4443_team7_project.ui.components.QuestCard
import com.example.eecs4443_team7_project.util.TrialTimerManager
import com.example.eecs4443_team7_project.util.UserPathCounter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun QuestsScreen(
    onPointsUpdated: (Int) -> Unit
) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE) }
    val trackerPrefs = context.getSharedPreferences("trackers_prefs", Context.MODE_PRIVATE)

    // Check for daily reset at midnight
    val lastResetDate = prefs.getString("last_reset_date", "")
    val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    // Track which quests are completed in local state (backed by SharedPreferences)
    var completedQuestIds by remember {
        val currentCompleted = if (lastResetDate != todayDate) {
            // Clear the completed quests
            prefs.edit { 
                putStringSet("completed_ids", emptySet()) 
                putString("last_reset_date", todayDate)
            }
            emptySet<String>()
        } else {
            prefs.getStringSet("completed_ids", emptySet()) ?: emptySet()
        }
        mutableStateOf(currentCompleted)
    }

    // Hardcoded list of predefined quests
    val allQuests = remember {
        listOf(
            Quest("q1", context.getString(R.string.in_your_feels), context.getString(R.string.log_feelings), 10, QuestCategory.MENTAL),
            Quest("q2", context.getString(R.string.i_affirm), context.getString(R.string.todays_affirmation), 5, QuestCategory.MENTAL),
            Quest("q3", context.getString(R.string.notice), context.getString(R.string.notice_something), 10, QuestCategory.MENTAL),
            Quest("q4", context.getString(R.string.hydration), context.getString(R.string.drink_water), 15, QuestCategory.PHYSICAL),
            Quest("q5", context.getString(R.string.nature_walk), context.getString(R.string.go_walk), 20, QuestCategory.PHYSICAL),
            Quest("q6", context.getString(R.string.quick_stretches), context.getString(R.string.stretch), 15, QuestCategory.PHYSICAL),
            Quest("q7", context.getString(R.string.declutter), context.getString(R.string.declutter_space), 20, QuestCategory.HABIT),
            Quest("q8", context.getString(R.string.yap_time), context.getString(R.string.communicate_friend), 15, QuestCategory.SOCIAL),
            )
    }

    // Split quests into active and completed, and show completed at the bottom
    val activeQuests = allQuests.filter { it.id !in completedQuestIds }
    val completedQuests = allQuests.filter { it.id in completedQuestIds }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp) // Space for bottom bar
        ) {
            // Active quests (claimable)
            items(activeQuests) { quest ->
                val isFeatured = quest.id == "q2"
                val onComplete: () -> Unit = when (quest.id) {
                    // q4 logic scrapped
                    else -> {
                        {
                            UserPathCounter.logUserPathEvent(TrialTimerManager.currentTask)
                            // Update local state
                            val newCompleted = completedQuestIds + quest.id
                            completedQuestIds = newCompleted
                            // Save to SharedPreferences
                            prefs.edit { putStringSet("completed_ids", newCompleted) }
                            // Reward points globally (double for affirm quest)
                            val rewardPoints = if (isFeatured) quest.points * 2 else quest.points
                            onPointsUpdated(rewardPoints)
                            // Show point reward toast
                            Toast.makeText(context, "Quest completed! Earned $rewardPoints points", Toast.LENGTH_SHORT).show()
                            //increment the count for tracker when quest completed
                            val trackerKey = when (quest.id) {
                                "q1" -> "mood"
                                "q2" -> "affirm"
                                "q3" -> "notice"
                                "q4" -> "water"
                                "q5" -> "walk"
                                "q6" -> "stretch"
                                "q7" -> "declutter"
                                "q8" -> "message"
                                else -> null
                            }
                            trackerKey?.let { key ->
                                val current = trackerPrefs.getInt(key, 0)
                                trackerPrefs.edit { putInt(key, current + 1) }
                            }
                            // Trial: Advance trial timer if a trial task is running (tasks 1-4)
                            if (TrialTimerManager.currentTask in 1..4) {
                                TrialTimerManager.nextTask(context)
                            }
                        }
                    }
                }
                QuestCard(
                    quest = quest,
                    onComplete = onComplete,
                    completed = false,
                    isFeatured = isFeatured
                )
            }
            // Completed quests grayed and sent to bottom
            items(completedQuests) { quest ->
                QuestCard(
                    quest = quest,
                    onComplete = {},
                    completed = true
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestsScreenPreview() {
    QuestsScreen(onPointsUpdated = {})
}
