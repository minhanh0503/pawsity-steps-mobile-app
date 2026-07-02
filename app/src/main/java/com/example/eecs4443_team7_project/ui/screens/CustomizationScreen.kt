package com.example.eecs4443_team7_project.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
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
import com.example.eecs4443_team7_project.util.PetManager
import com.example.eecs4443_team7_project.util.SoundManager

@Composable
fun CustomizationScreen(onComplete: (Pet) -> Unit) {
    val context = LocalContext.current
    
    // Unified state for the pet configuration to allow UI updates during customization
    var petState by remember { 
        mutableStateOf(
            Pet(
                name = "",
                colour = PetColour.LAVENDER,
                face = PetFace.HAPPY, 
                headAccessory = null, 
                bodyAccessory = null
            )
        ) 
    }

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

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {

            // Text input for Pet Name
            OutlinedTextField(
                value = petState.name,
                onValueChange = { petState = petState.copy(name = it) },
                label = { Text(stringResource(R.string.pet_name)) },
                placeholder = { Text(stringResource(R.string.enter_name)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Left Column: Previous selections (Arrows point LEFT)
                Column(
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Previous Face
                    FilledTonalIconButton(
                        onClick = {
                            SoundManager.playBonkSound()
                            val faces = PetFace.entries
                            val currentIndex = faces.indexOf(petState.face)
                            val prevIndex = (currentIndex - 1 + faces.size) % faces.size
                            petState = petState.copy(face = faces[prevIndex])
                        },
                        modifier = Modifier.size(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = colorResource(R.color.yellow_orange)
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow),
                            contentDescription = stringResource(R.string.previous_face),
                            modifier = Modifier
                                .size(32.dp)
                                .padding(bottom = 4.dp)
                        )
                    }

                    // Previous Color
                    FilledTonalIconButton(
                        onClick = {
                            SoundManager.playBonkSound()
                            val colours = PetColour.entries
                            val currentIndex = colours.indexOf(petState.colour)
                            val prevIndex = (currentIndex - 1 + colours.size) % colours.size
                            petState = petState.copy(colour = colours[prevIndex])
                        },
                        modifier = Modifier.size(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = colorResource(R.color.yellow_orange)
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow),
                            contentDescription = stringResource(R.string.previous_colour),
                            modifier = Modifier
                                .size(32.dp)
                        )
                    }
                }

                // Display the Pet Avatar - using weight(1f) to ensure it shrinks to fit between arrows
                PetAvatar(
                    pet = petState,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .sizeIn(maxWidth = 250.dp)
                        .aspectRatio(1f)
                )

                // Right Column: Next selections (Arrows point RIGHT)
                Column(
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Next Face
                    FilledTonalIconButton(
                        onClick = {
                            SoundManager.playBonkSound()
                            val faces = PetFace.entries
                            val currentIndex = faces.indexOf(petState.face)
                            val nextIndex = (currentIndex + 1) % faces.size
                            petState = petState.copy(face = faces[nextIndex])
                        },
                        modifier = Modifier.size(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = colorResource(R.color.yellow_orange)
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow),
                            contentDescription = stringResource(R.string.next_face),
                            modifier = Modifier
                                .size(32.dp)
                                .scale(scaleX = -1f, scaleY = 1f) // Point left
                        )
                    }

                    // Next Color
                    FilledTonalIconButton(
                        onClick = {
                            SoundManager.playBonkSound()
                            val colours = PetColour.entries
                            val currentIndex = colours.indexOf(petState.colour)
                            val nextIndex = (currentIndex + 1) % colours.size
                            petState = petState.copy(colour = colours[nextIndex])
                        },
                        modifier = Modifier.size(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = colorResource(R.color.yellow_orange)
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow),
                            contentDescription = stringResource(R.string.next_colour),
                            modifier = Modifier
                                .size(32.dp)
                                .scale(scaleX = -1f, scaleY = 1f) // Point left
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Save and Continue button
            Button(
                onClick = {
                    SoundManager.playBonkSound()
                    if (petState.name.isNotBlank()) {
                        PetManager.savePet(context, petState)
                        onComplete(petState)
                    }
                },
                enabled = petState.name.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.yellow_orange) // Set the button color here
                ),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text(stringResource(R.string.start_journey), style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomizationScreenPreview() {
    CustomizationScreen(onComplete = {})
}
