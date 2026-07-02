package com.example.eecs4443_team7_project.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import com.example.eecs4443_team7_project.util.TrialTimerManager
import com.example.eecs4443_team7_project.util.UserPathCounter

@Composable
fun SettingsScreen(onNavigate: (String) -> Unit, onReset: () -> Unit) {
    val context = LocalContext.current
    
    // If entering Settings with currentTask 3 or 4, advance the trial
    LaunchedEffect(Unit) {
        if (TrialTimerManager.currentTask in 3..4 && !TrialTimerManager.trialComplete) {
            UserPathCounter.logUserPathEvent(TrialTimerManager.currentTask)
            TrialTimerManager.nextTask(context)
        }
    }

    val prefs = context.getSharedPreferences("nav_prefs", android.content.Context.MODE_PRIVATE)
    val telemetryPrefs = context.getSharedPreferences("telemetry_prefs", android.content.Context.MODE_PRIVATE)
    val inventoryPrefs = context.getSharedPreferences("inventory_prefs", android.content.Context.MODE_PRIVATE)
    val questPrefs = context.getSharedPreferences("quest_prefs", android.content.Context.MODE_PRIVATE)
    val petPrefs = context.getSharedPreferences("pet_prefs", android.content.Context.MODE_PRIVATE)
    val musicPrefs = context.getSharedPreferences("music_prefs", android.content.Context.MODE_PRIVATE)
    val navOption = prefs.getInt("nav_option", -1)

    var volume by remember { mutableStateOf(musicPrefs.getFloat("bgm_volume", 0.3f)) }

    fun resetAllState() {
        // Reset trial state
        TrialTimerManager.reset()
        // Reset navigation prefs
        prefs.edit(commit = true) {
            putInt("nav_option", -1)
            putBoolean("customization_complete", false)
            putInt("user_points", 100)
        }
        // Reset telemetry prefs
        telemetryPrefs.edit(commit = true) {
            putString("participant", "")
            putBoolean("isCollecting", false)
            putBoolean("showTelemetryModal", true)
        }
        // Reset inventory
        inventoryPrefs.edit(commit = true) {
            putStringSet("owned_items", emptySet())
        }
        // Reset quests
        questPrefs.edit(commit = true) {
            putStringSet("completed_ids", emptySet())
        }
        // Reset pet
        petPrefs.edit(commit = true) {
            putString("pet_colour", "LAVENDER")
            putString("pet_face", "HAPPY")
            remove("pet_head")
            remove("pet_body")
            putLong("pet_adoption_date", 0L)
        }
        // Optionally reset other model-specific prefs here
        // Restart activity for clean state
        (context as? android.app.Activity)?.recreate()
    }

    // Show trial complete toast only once per trial completion
    val trialDialogPrefs = remember { context.getSharedPreferences("telemetry_prefs", android.content.Context.MODE_PRIVATE) }
    
    // Check if toast has already been shown for this trial
    LaunchedEffect(TrialTimerManager.trialComplete) {
        if (TrialTimerManager.trialComplete) {
            val alreadyShown = trialDialogPrefs.getBoolean("trial_complete_dialog_shown", false)
            if (!alreadyShown) {
                Toast.makeText(context, "Trial recorded, thank you!", Toast.LENGTH_LONG).show()
                trialDialogPrefs.edit { putBoolean("trial_complete_dialog_shown", true) }
            }
        } else {
            // Reset the flag when a new trial starts (trialComplete becomes false)
            trialDialogPrefs.edit { putBoolean("trial_complete_dialog_shown", false) }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Settings Screen")
            Spacer(modifier = Modifier.height(16.dp))
            // TELEMETRY: Remove this block and the TelemetryScreen reference to fully remove telemetry from the app.
            Button(onClick = {
                onNavigate("telemetry")
            }) {
                Text("View Telemetry Data")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Background Music Volume")
            Slider(
                value = volume,
                onValueChange = {
                    volume = it
                    (context as? com.example.eecs4443_team7_project.ui.MainActivity)?.setMusicVolume(it)
                    musicPrefs.edit { putFloat("bgm_volume", it) }
                },
                valueRange = 0f..1f,
                steps = 8,
                modifier = Modifier.width(200.dp)
            )
        }
        // DEBUG section at bottom
        val navLabel = when (navOption) {
            0 -> "Bottom Bar Nav Only"
            1 -> "Hamburger Nav Only"
            2 -> "Bottom+Burger Nav"
            else -> "Unknown"
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("DEBUG", color = Color.Red)
            Text(
                text = "You have selected $navLabel option.",
                color = Color(0xFF388E3C)
            )
            Button(onClick = {
                onReset()
            }) {
                Text("Back to Selection")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                resetAllState()
                onReset()
            }) {
                Text("Reset State (for testing)")
            }
        }
    }
}
