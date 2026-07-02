package com.example.eecs4443_team7_project.models

import androidx.annotation.DrawableRes
import com.example.eecs4443_team7_project.R

data class Track(
    val key: String,                 // used for SharedPreferences
    val label: Int,               // string.xml "You've logged on %d times!"
    val category: TrackCategory      // reuse category idea like Quest
)

enum class TrackCategory(
    val displayName: String,
    @DrawableRes val iconRes: Int
) {
    ACHIEVEMENT("Achievement", R.drawable.ic_achievement),
    MENTAL("Mental", R.drawable.ic_mental),
    PHYSICAL("Physical", R.drawable.ic_physical),
    SOCIAL("Social", R.drawable.ic_social),
    HABIT("Habit", R.drawable.ic_habit)
}