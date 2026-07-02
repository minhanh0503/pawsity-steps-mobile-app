package com.example.eecs4443_team7_project.models

/**
 * Holds all summary telemetry data for display in TelemetryScreen.
 */
data class TelemetrySummary(
    val participantName: String,
    val uiType: String, // "A", "B", or "C"
    val timePerTask: List<Double>, // seconds
    val errorRatePerTask: List<Int>,
    val batteryPerTask: List<Int>
)

