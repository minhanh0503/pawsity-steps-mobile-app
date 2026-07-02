package com.example.eecs4443_team7_project.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eecs4443_team7_project.R
import com.example.eecs4443_team7_project.models.BodyAccessory
import com.example.eecs4443_team7_project.models.HeadAccessory
import com.example.eecs4443_team7_project.models.Pet
import com.example.eecs4443_team7_project.models.PetColour
import com.example.eecs4443_team7_project.models.PetFace
import com.example.eecs4443_team7_project.ui.components.PetAvatar
import com.example.eecs4443_team7_project.util.SoundManager
import com.example.eecs4443_team7_project.util.TrialTimerManager
import com.example.eecs4443_team7_project.util.UserPathCounter

@Composable
fun ClosetScreen(
    currentPet: Pet,
    onPetChanged: (Pet) -> Unit,
    onNavigate: (String) -> Unit
) {
    val context = LocalContext.current
    val inventoryPrefs = remember { context.getSharedPreferences("inventory_prefs", Context.MODE_PRIVATE) }
    
    // Load owned items from shop purchases
    val ownedItemNames = remember { 
        inventoryPrefs.getStringSet("owned_items", emptySet()) ?: emptySet() 
    }

    val ownedHead = HeadAccessory.entries.filter { ownedItemNames.contains(it.name) }
    val ownedBody = BodyAccessory.entries.filter { ownedItemNames.contains(it.name) }

    var showHeadAccessories by remember { mutableStateOf(true) }
    var showBodyAccessories by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (
                modifier = Modifier.wrapContentWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Head Selection Button
                FilledTonalIconButton(
                    onClick = {
                        SoundManager.playBonkSound()
                        UserPathCounter.logUserPathEvent(TrialTimerManager.currentTask)
                        showHeadAccessories = true
                        showBodyAccessories = false 
                    },
                    modifier = Modifier.size(64.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = if (showHeadAccessories) colorResource(R.color.dark_yellow_orange) else colorResource(R.color.yellow_orange)
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_head),
                        contentDescription = stringResource(R.string.head_accessories),
                        modifier = Modifier.size(32.dp)
                    )
                }

                // Body Selection Button
                FilledTonalIconButton(
                    onClick = {
                        SoundManager.playBonkSound()
                        UserPathCounter.logUserPathEvent(TrialTimerManager.currentTask)
                        showHeadAccessories = false
                        showBodyAccessories = true 
                    },
                    modifier = Modifier.size(64.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = if (showBodyAccessories) colorResource(R.color.dark_yellow_orange) else colorResource(R.color.yellow_orange)
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_body),
                        contentDescription = stringResource(R.string.body_accessories),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            PetAvatar(
                pet = currentPet,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .sizeIn(maxWidth = 300.dp)
                    .aspectRatio(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))

        if (showHeadAccessories) {
            ClosetGrid(
                items = ownedHead, 
                selectedName = currentPet.headAccessory?.name, 
                isHeadCategory = true,
                modifier = Modifier.weight(1f),
                onSelect = { selected ->
                    UserPathCounter.logUserPathEvent(TrialTimerManager.currentTask)
                    val newPet = currentPet.copy(headAccessory = if (currentPet.headAccessory == selected) null else selected as HeadAccessory)
                    onPetChanged(newPet)

                    // Check and advance trial timer if necessary
                    if (TrialTimerManager.currentTask == 3) {
                        TrialTimerManager.nextTask(context)
                    }
                }
            )
        }

        if (showBodyAccessories) {
            ClosetGrid(
                items = ownedBody, 
                selectedName = currentPet.bodyAccessory?.name, 
                isHeadCategory = false,
                modifier = Modifier.weight(1f),
                onSelect = { selected ->
                    UserPathCounter.logUserPathEvent(TrialTimerManager.currentTask)
                    val newPet = currentPet.copy(bodyAccessory = if (currentPet.bodyAccessory == selected) null else selected as BodyAccessory)
                    onPetChanged(newPet)

                    // Check and advance trial timer if necessary
                    if (TrialTimerManager.currentTask == 3) {
                        TrialTimerManager.nextTask(context)
                    }
                }
            )
        }
    }
}

@Composable
fun ClosetGrid(
    items: List<Any>, 
    selectedName: String?, 
    isHeadCategory: Boolean,
    onSelect: (Any) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        // Background Box (drawn behind)
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    color = colorResource(R.color.matcha_green),
                    shape = RoundedCornerShape(16.dp)
                )
        )

        if (items.isEmpty()) {
            Text(
                text = "Nothing here yet. Visit the shop!", 
                color = colorResource(R.color.black), 
                modifier = Modifier.padding(24.dp).align(Alignment.Center)
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items) { item ->
                    val resId = when(item) {
                        is HeadAccessory -> item.resId
                        is BodyAccessory -> item.resId
                        else -> 0
                    }
                    val name = when(item) {
                        is HeadAccessory -> item.name
                        is BodyAccessory -> item.name
                        else -> ""
                    }

                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .background(colorResource(R.color.yellow_orange), RoundedCornerShape(12.dp))
                            .border(
                                width = if (selectedName == name) 6.dp else 0.dp,
                                color = if (selectedName == name) colorResource(R.color.dark_yellow_orange) else Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                SoundManager.playBonkSound()
                                onSelect(item) },
                        contentAlignment = Alignment.Center
                    ) {
                        val alignmentType = if (isHeadCategory) Alignment.TopCenter else BiasAlignment(
                            horizontalBias = 0f,
                            verticalBias = 0.6f
                        )
                        val topPaddingType = if (isHeadCategory) 37.dp else 0.dp

                        Image(
                            painter = painterResource(resId),
                            contentDescription = name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = topPaddingType)
                                .scale(1.4f),
                            contentScale = ContentScale.Crop,
                            alignment = alignmentType,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClosetScreenPreview() {
    ClosetScreen(
        currentPet = Pet("Luna", PetColour.LAVENDER, PetFace.HAPPY, null, null),
        onPetChanged = {},
        onNavigate = {}
    )
}

@Preview
@Composable
fun ClosetGridPreview() {
    ClosetGrid(
        items = listOf(
            HeadAccessory.WITCH,
            HeadAccessory.CROWN,
            HeadAccessory.PROPELLER),
        isHeadCategory = true,
        selectedName = null,
        onSelect = {}
    )
}
