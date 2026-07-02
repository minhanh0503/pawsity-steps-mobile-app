package com.example.eecs4443_team7_project.util

/**
 * Tracks user path counts (button presses) per task for telemetry.
 */
object UserPathCounter {
    fun logUserPathEvent(currentTask: Int) {
        val idx = currentTask - 1
        if (idx in 0..3) increment(idx)
    }
    private val counts = IntArray(4) { 0 } // 4 tasks, it will say inefficient but this ensures each task path is reset to 0

    fun increment(taskIndex: Int) {
        if (taskIndex in counts.indices) {
            counts[taskIndex]++
            android.util.Log.d(
                "UserPathCounter",
                "task${taskIndex + 1}, click recorded, ${counts[taskIndex]}"
            )
        }
    }

    fun getCounts(): List<Int> = counts.toList()

    fun reset() {
        for (i in counts.indices) counts[i] = 0
    }
}
