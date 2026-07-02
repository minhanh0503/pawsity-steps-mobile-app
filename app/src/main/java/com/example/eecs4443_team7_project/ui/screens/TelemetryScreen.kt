// TELEMETRY: Remove all code in this file to fully remove telemetry from the app.

package com.example.eecs4443_team7_project.ui.screens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.eecs4443_team7_project.models.TelemetrySummary

@Composable
fun TelemetryScreen(
    summary: TelemetrySummary,
    onNavigate: (String) -> Unit
) {
    Log.d("", "TelemetryScreen: Composable invoked")
    val context = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "PARTICIPANT: ${summary.participantName}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "UI TYPE ${summary.uiType}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))

            // TIME PER TASK TABLE
            Text(
                text = "TIME PER TASK",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "TASK NUMBER (n) | TIME (s)",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            for (i in 0 until 4) {
                val timeSec = if (i < summary.timePerTask.size) String.format("%.2f", summary.timePerTask[i]) else "-"
                Text(
                    text = "${i+1}               | $timeSec",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ERROR RATE PER TASK TABLE
            Text(
                text = "ERROR RATE PER TASK",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "TASK NUMBER (n) | ERR (n)",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            for (i in 0 until 4) {
                val err = if (i < summary.errorRatePerTask.size) summary.errorRatePerTask[i].toString() else "-"
                Text(
                    text = "${i+1}               | $err",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // BATTERY PERCENTAGE BY TASK TABLE
            Text(
                text = "BATTERY PERCENTAGE BY TASK",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "TASK NUMBER (n) | BATT (%)",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            for (i in 0 until 4) {
                val batt = if (i < summary.batteryPerTask.size) summary.batteryPerTask[i].toString() else "-"
                Text(
                    text = "${i+1}               | $batt",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Export Button
            Button(onClick = {
                // Format telemetry data as text
                val exportText = buildString {
                    appendLine("PARTICIPANT: ${summary.participantName}")
                    appendLine("UI TYPE ${summary.uiType}")
                    appendLine()
                    appendLine("TIME PER TASK")
                    appendLine("TASK NUMBER (n) | TIME (s)")
                    for (i in 0 until 4) {
                        val timeSec = if (i < summary.timePerTask.size) String.format("%.2f", summary.timePerTask[i]) else "-"
                        appendLine("${i+1}               | $timeSec")
                    }
                    appendLine()
                    appendLine("ERROR RATE PER TASK")
                    appendLine("TASK NUMBER (n) | ERR (n)")
                    for (i in 0 until 4) {
                        val err = if (i < summary.errorRatePerTask.size) summary.errorRatePerTask[i].toString() else "-"
                        appendLine("${i+1}               | $err")
                    }
                    appendLine()
                    appendLine("BATTERY PERCENTAGE BY TASK")
                    appendLine("TASK NUMBER (n) | BATT (%)")
                    for (i in 0 until 4) {
                        val batt = if (i < summary.batteryPerTask.size) summary.batteryPerTask[i].toString() else "-"
                        appendLine("${i+1}               | $batt")
                    }
                }
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, exportText)
                }
                val chooser = Intent.createChooser(intent, "Export Telemetry Data")
                context.startActivity(chooser)
            }) {
                Text("Export")
            }
        }
    }
}
