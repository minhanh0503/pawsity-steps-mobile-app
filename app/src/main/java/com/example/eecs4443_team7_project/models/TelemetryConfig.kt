package com.example.eecs4443_team7_project.models

object TelemetryConfig {
    // Set to false to fully disable telemetry collection and UI
    const val ENABLE_TELEMETRY = true

    // Optimal (fewest) clicks per task for each UI navigation type
    // Index corresponds to task number (0: Task 1, 1: Task 2, etc.)
    private val OPTIMAL_CLICKS_A = listOf(2, 2, 2, 2) // Bottom Bar
    private val OPTIMAL_CLICKS_B = listOf(3, 3, 3, 3) // Hamburger
    private val OPTIMAL_CLICKS_C = listOf(2, 2, 3, 3) // Hybrid (assumed)

    fun getOptimalPath(uiType: String): List<Int> {
        return when (uiType) {
            "A" -> OPTIMAL_CLICKS_A
            "B" -> OPTIMAL_CLICKS_B
            "C" -> OPTIMAL_CLICKS_C
            else -> listOf(0, 0, 0, 0)
        }
    }
}
