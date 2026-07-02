package com.example.eecs4443_team7_project.ui.screens

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import com.example.eecs4443_team7_project.R

@Composable
fun EntryPointScreen(
    prefs: SharedPreferences,
    onNavOptionSet: (Int) -> Unit
) {
    val context = LocalContext.current
    var navMode by remember { mutableIntStateOf(-1) }
    var showError by remember { mutableStateOf(false) }
    var participant by remember { mutableStateOf("") }
    val navOptions = listOf(
        "Bottom Bar Nav Only",
        "Hamburger Nav Only",
        "Hybrid Navigation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.sea_green))
    ) {

        Image(
            painter = painterResource(R.drawable.trail_graphic),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 220.dp),
            contentScale = ContentScale.FillWidth
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pawsitive Steps",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = participant,
            onValueChange = { participant = it },
            label = { Text("Participant Name") },
            modifier = Modifier.padding(vertical = 8.dp),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                if (navMode == -1 || participant.isBlank()) {
                    showError = true
                } else {
                    showError = false
                    prefs.edit { putInt("nav_option", navMode) }
                    val telemetryPrefs = context.getSharedPreferences("telemetry_prefs", android.content.Context.MODE_PRIVATE)
                    telemetryPrefs.edit { putString("participant", participant) }
                    onNavOptionSet(navMode)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.yellow_orange),
                contentColor = colorResource(R.color.black)
            )
        ) {
            Text("Start")
        }
        if (showError) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Please select a navigation style and enter participant name.",
                color = Color.Red
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        navOptions.forEachIndexed { idx, label ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .testTag("nav_option_row_$idx")
                    .clickable {
                        navMode = idx
                        showError = false
                    }
            ) {
                RadioButton(
                    selected = navMode == idx,
                    onClick = null, // Row handles click
                    modifier = Modifier.testTag("nav_option_$idx"),
                    colors = RadioButtonDefaults.colors()
                )
                Text(
                    text = label,
                    modifier = Modifier.testTag("nav_option_label_$idx")
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EntryPointScreenPreview() {
    EntryPointScreen(
        prefs = LocalContext.current.getSharedPreferences("dummy", 0),
        onNavOptionSet = {}
    )
}
