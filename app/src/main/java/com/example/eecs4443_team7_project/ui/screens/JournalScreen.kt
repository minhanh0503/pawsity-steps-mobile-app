package com.example.eecs4443_team7_project.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import com.example.eecs4443_team7_project.R
import com.example.eecs4443_team7_project.models.Pet
import com.example.eecs4443_team7_project.models.PetColour
import com.example.eecs4443_team7_project.models.PetFace
import com.example.eecs4443_team7_project.models.Quest
import com.example.eecs4443_team7_project.ui.components.PetAvatar
import com.example.eecs4443_team7_project.ui.components.QuestCard
import com.example.eecs4443_team7_project.ui.navigation.Routes
import com.example.eecs4443_team7_project.util.SoundManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun JournalScreen(
    pet: Pet,
    onNavigate: (String) -> Unit
) {
    val context = LocalContext.current
    val formattedDate = remember(pet.adoptionDate) {
        val sdf = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        sdf.format(Date(pet.adoptionDate))
    }

    // Use SharedPreferences to track completion state for the boosted quest (affirmation)
    val prefs = remember { context.getSharedPreferences("quest_prefs", Context.MODE_PRIVATE) }
    val lastResetDate = prefs.getString("last_reset_date", "")
    val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    val completedIds = remember {
        if (lastResetDate != todayDate) {
            prefs.edit {
                putStringSet("completed_ids", emptySet())
                putString("last_reset_date", todayDate)
            }
            emptySet<String>()
        } else {
            prefs.getStringSet("completed_ids", emptySet()) ?: emptySet()
        }
    }
    var boostedCompleted by remember { mutableStateOf("q2" in completedIds) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.yellow_orange)
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .background(colorResource(R.color.dark_yellow_orange))
                        .border(
                            width = 1.dp,
                            color = colorResource(R.color.black),
                        ),
                ) {
                    PetAvatar(
                        pet = pet,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(top = 4.dp),
                        isHeadOnly = true
                    )
                }

                Text(
                    text = stringResource(R.string.journal_msg, pet.name, formattedDate),
                    style = MaterialTheme.typography.labelLarge,
                    color = colorResource(R.color.black),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp)
                )

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val cardHeight = 100.dp
            // Tracker button
            FilledTonalIconButton(
                onClick = {
                    SoundManager.playBonkSound()
                    onNavigate(Routes.TRACKER)
                },
                modifier = Modifier
                    .weight(0.33f)
                    .height(cardHeight-16.5.dp),
                shape = RoundedCornerShape(12.dp),
                colors = IconButtonDefaults.filledTonalIconButtonColors(colorResource(R.color.yellow_orange)
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_tracker),
                        contentDescription = stringResource(R.string.tracker),
                        modifier = Modifier
                            .size(40.dp)
                            .padding(bottom = 4.dp),
                    )

                    Text(
                        text = stringResource(R.string.tracker),
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            if (!boostedCompleted) {
                // Boosted daily quest button (affirmation)
                QuestCard(
                    quest = Quest(
                        id = "q2",
                        title = stringResource(R.string.i_affirm),
                        description = stringResource(R.string.todays_affirmation),
                        points = 5,
                        category = com.example.eecs4443_team7_project.models.QuestCategory.MENTAL
                    ),
                    onComplete = {
                        // Mark as completed in SharedPreferences
                        val newCompleted = completedIds + "q2"
                        prefs.edit { putStringSet("completed_ids", newCompleted) }
                        boostedCompleted = true
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(cardHeight),
                    completed = false
                )
            } else {
                // Show congratulatory message in a dark yellow card
                QuestCard(
                    quest = Quest(
                        id = "q2",
                        title = stringResource(R.string.i_affirm),
                        description = stringResource(R.string.todays_affirmation),
                        points = 5,
                        category = com.example.eecs4443_team7_project.models.QuestCategory.MENTAL
                    ),
                    onComplete = {},
                    modifier = Modifier
                        .weight(1f)
                        .height(cardHeight),
                    completed = true,
                    completedMessage = "Nice job! Stay pawsitive!",
                    completedMessageTextStyle = MaterialTheme.typography.labelSmall // Pass a smaller text style
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JournalScreenPreview() {
    JournalScreen (
        pet = Pet("Luna", PetColour.LAVENDER, PetFace.HAPPY, null, null, System.currentTimeMillis()),
        onNavigate = {}
    )
}
