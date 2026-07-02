package com.example.eecs4443_team7_project.util

import android.content.Context
import android.os.BatteryManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * This file is responsible for tracking metrics collected in our participant trials, capturing the
 * telemetry data for participant trials.
 */
object TrialTimerManager {
    private var _currentTask by mutableStateOf(0) // 0: not started, 1-4: tasks, 5: complete
    val currentTask: Int get() = _currentTask
    private var startTime: Long = 0L
    private val times = mutableListOf<Long>()
    private val batteryLevels = mutableListOf<Int>()
    var trialComplete by mutableStateOf(false)
        private set

    fun startTrial(context: Context) {
        times.clear()
        batteryLevels.clear()
        _currentTask = 1
        trialComplete = false
        startTime = System.currentTimeMillis()
        UserPathCounter.reset()
    }

    fun nextTask(context: Context) {
        if (_currentTask in 1..4) {
            val elapsed = System.currentTimeMillis() - startTime
            times.add(elapsed)
            
            // Record battery level
            val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            val level = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
            batteryLevels.add(level)

            if (_currentTask == 4) {
                trialComplete = true
                _currentTask = 5
                android.util.Log.d("TrialFlow", "Trial Complete. Times: $times, Battery: $batteryLevels")
                val userPathCounts = UserPathCounter.getCounts()
                android.util.Log.d("TrialFlow", "User path counts per task: $userPathCounts")
            } else {
                _currentTask++
                startTime = System.currentTimeMillis()
                android.util.Log.d("TrialFlow", "Task $_currentTask started.")
            }
        }
    }

    fun getTimes(): List<Long> = times.toList()
    fun getBatteryLevels(): List<Int> = batteryLevels.toList()

    fun reset() {
        times.clear()
        batteryLevels.clear()
        _currentTask = 0
        trialComplete = false
        startTime = 0L
    }
}
