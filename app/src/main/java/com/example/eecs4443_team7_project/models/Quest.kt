package com.example.eecs4443_team7_project.models

import androidx.annotation.DrawableRes
import com.example.eecs4443_team7_project.R

data class Quest(
    val id: String,
    val title: String,
    val description: String,
    val points: Int,
    val category: QuestCategory,
    val isCompleted: Boolean = false
)

enum class QuestCategory(
    val displayName: String,
    @DrawableRes val iconRes: Int
) {
    PHYSICAL("Physical", R.drawable.ic_physical),
    MENTAL("Mental", R.drawable.ic_mental),
    HABIT("Habit", R.drawable.ic_habit),
    SOCIAL("Social", R.drawable.ic_social)
}
