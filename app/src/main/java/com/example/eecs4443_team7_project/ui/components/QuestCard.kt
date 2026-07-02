package com.example.eecs4443_team7_project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.eecs4443_team7_project.R
import com.example.eecs4443_team7_project.models.Affirmations
import com.example.eecs4443_team7_project.models.Quest
import com.example.eecs4443_team7_project.util.SoundManager

@Composable
fun QuestCard(
    quest: Quest,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
    completed: Boolean = quest.isCompleted,
    completedMessage: String? = null,
    completedMessageTextStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.titleMedium,
    isFeatured: Boolean = false
) {
    var showDialog by remember { mutableStateOf(false) }

    val cardColor = if (completed) colorResource(R.color.dark_yellow_orange) else colorResource(R.color.yellow_orange)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        if (completed && completedMessage != null) {
            // Show completed message only
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = completedMessage,
                    style = completedMessageTextStyle,
                    color = colorResource(R.color.black),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category Icon
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = colorResource(R.color.dark_yellow_orange),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(quest.category.iconRes),
                        contentDescription = quest.category.displayName,
                        modifier = Modifier.size(28.dp),
                        tint = colorResource(R.color.black)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Quest Details
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = quest.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.black)
                    )
                    Text(
                        text = quest.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.ic_paw),
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = colorResource(R.color.black)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isFeatured) "${quest.points * 2}" else "${quest.points}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isFeatured) colorResource(R.color.orange_orange) else colorResource(R.color.black)
                        )
                    }
                }

                // Complete Button
                Box {
                    Button(
                        onClick = {
                            if (quest.id == "q2") {
                                showDialog = true
                            } else {
                                onComplete()
                            }
                            SoundManager.playBonkSound()
                        },
                        enabled = !completed,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.dark_yellow_orange),
                            contentColor = colorResource(R.color.black)
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = if (completed) "Done" else "Claim",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    if (isFeatured && !completed) {
                        Surface(
                            color = colorResource(R.color.orange_orange),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .offset(x = (-8).dp, y = (-18).dp)
                        ) {
                            Text(
                                text = "x2",
                                color = colorResource(R.color.black),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            containerColor = colorResource(R.color.yellow_orange),
            titleContentColor = colorResource(R.color.black),
            textContentColor = colorResource(R.color.black),
            title = {
                Text(
                    text = quest.title,
                    fontWeight = FontWeight.Bold
                ) },
            text = {
                Text(text = Affirmations.list.random())
                Spacer(modifier = Modifier.height(24.dp))
            },
            confirmButton = {
                TextButton(onClick = {
                    SoundManager.playBonkSound()
                    onComplete()
                    showDialog = false
                }) {
                    Text(
                        text = "I affirm",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.black)
                    )
                }
            }
        )
    }
}
