package com.example.eecs4443_team7_project.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eecs4443_team7_project.R
import com.example.eecs4443_team7_project.models.Pet
import com.example.eecs4443_team7_project.models.PetColour
import com.example.eecs4443_team7_project.models.PetFace
import com.example.eecs4443_team7_project.ui.components.PetAvatar
import com.example.eecs4443_team7_project.ui.navigation.Routes
import com.example.eecs4443_team7_project.util.SoundManager
import com.example.eecs4443_team7_project.util.TrialTimerManager
import java.util.Calendar

@Composable
fun HomeScreen(
    pet: Pet,
    onNavigate: (String) -> Unit,
    fromCustomization: Boolean = false
) {

    val context = LocalContext.current
    // Only show the modal the first time after app launch, not on every navigation to Home
    val prefs = context.getSharedPreferences("telemetry_prefs", Context.MODE_PRIVATE)
    var showTelemetryModal by remember {
        mutableStateOf(prefs.getBoolean("showTelemetryModal", true))
    }

    // Determine greeting based on system clock
    val greeting = remember {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        when (hour) {
            in 5..11 -> R.string.good_morning
            in 12..16 -> R.string.good_afternoon
            in 17..20 -> R.string.good_evening
            else -> R.string.good_night
        }
    }

    var showTrialModal by remember { mutableStateOf(false) }
    var localFromCustomization by remember { mutableStateOf(fromCustomization) }
    // Show modal if trial not started or if just came from CustomizationScreen
    LaunchedEffect(localFromCustomization, TrialTimerManager.currentTask, TrialTimerManager.trialComplete) {
        showTrialModal = (TrialTimerManager.currentTask == 0 && !TrialTimerManager.trialComplete) || localFromCustomization
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Text Bubble with Greeting
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_text_bubble),
                    contentDescription = null,
                    modifier = Modifier.size(width = 190.dp, height = 110.dp)
                )
                Text(
                    text = stringResource(greeting),
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorResource(R.color.black),
                    modifier = Modifier.padding(bottom = 4.dp) // Offset for bubble tail
                )
            }

            // Pet Avatar
            PetAvatar(
                pet = pet,
                modifier = Modifier.size(300.dp)
            )
        }

        // TELEMETRY: Remove this block to remove the floating settings button
        // Floating Action Button
        FloatingActionButton(
            onClick = {
                onNavigate(Routes.SETTINGS)
                SoundManager.playBonkSound() },
            containerColor = colorResource(R.color.yellow_orange),
            contentColor = colorResource(R.color.black),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_gear),
                contentDescription = stringResource(R.string.settings),
                modifier = Modifier.size(24.dp)
            )
        }
        // Trial Start Modal
        if (showTrialModal) {
            AlertDialog(
                onDismissRequest = {
                    showTrialModal = false
                    localFromCustomization = false // Reset so modal only appears once after UI change
                },
                title = { Text("Trial Timer") },
                text = { Text("Press Start to begin the trial timing.") },
                confirmButton = {
                    TextButton(onClick = {
                        TrialTimerManager.startTrial(context)
                        showTrialModal = false
                        localFromCustomization = false // Reset so modal only appears once after UI change
                    }) {
                        Text("Start")
                    }
                },
                dismissButton = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        pet = Pet("Luna", PetColour.LAVENDER, PetFace.HAPPY, null, null),
        onNavigate = {}
    )
}
