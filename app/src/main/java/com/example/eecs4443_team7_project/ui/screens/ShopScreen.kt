package com.example.eecs4443_team7_project.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import com.example.eecs4443_team7_project.R
import com.example.eecs4443_team7_project.models.BodyAccessory
import com.example.eecs4443_team7_project.models.HeadAccessory
import com.example.eecs4443_team7_project.util.SoundManager
import com.example.eecs4443_team7_project.util.TrialTimerManager
import com.example.eecs4443_team7_project.util.UserPathCounter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(
    currentPoints: Int,
    onPointsUpdated: (Int) -> Unit
) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("inventory_prefs", Context.MODE_PRIVATE) }
    
    // Track owned items
    var ownedItems by remember { 
        mutableStateOf(prefs.getStringSet("owned_items", emptySet()) ?: emptySet()) 
    }

    val headItems = HeadAccessory.entries
    val bodyItems = BodyAccessory.entries

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Random Purchase Card
        Card(
            onClick = {
                SoundManager.playKachingSound()
                UserPathCounter.logUserPathEvent(TrialTimerManager.currentTask)
                val price = 5
                if (currentPoints >= price) {
                    val allItems = headItems + bodyItems
                    val unownedItems = allItems.filter { it.name !in ownedItems }
                    
                    if (unownedItems.isNotEmpty()) {
                        val randomItem = unownedItems.random()
                        onPointsUpdated(-price)
                        val newOwned = ownedItems + randomItem.name
                        ownedItems = newOwned
                        prefs.edit { putStringSet("owned_items", newOwned) }
                        Toast.makeText(context, "You got ${randomItem.name}!", Toast.LENGTH_SHORT).show()

                        // Check and advance trial timer if a trial task is running (tasks 1-4)
                        if (TrialTimerManager.currentTask in 1..4) {
                            TrialTimerManager.nextTask(context)
                        }
                    } else {
                        Toast.makeText(context, "You already own everything!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Not enough points!", Toast.LENGTH_SHORT).show()
                }
            },
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
                Image(
                    painter = painterResource(R.drawable.ic_catnip),
                    contentDescription = null,
                    modifier = Modifier
                        .size(92.dp)
                        .background(colorResource(R.color.dark_yellow_orange))
                        .padding(8.dp),
                )

                Text(
                    text = stringResource(R.string.catnip_msg),
                    style = MaterialTheme.typography.labelLarge,
                    color = colorResource(R.color.black),
                    modifier = Modifier.weight(1f).padding(horizontal = 12.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_paw),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        text = "5",
                        style = MaterialTheme.typography.labelLarge,
                        color = colorResource(R.color.black)
                    )
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(headItems) { item ->
                ShopItemCard(
                    name = item.name,
                    iconRes = item.resId,
                    price = item.price,
                    isHeadAccessory = true,
                    isOwned = ownedItems.contains(item.name),
                    canAfford = currentPoints >= item.price,
                    onPurchase = {
                        UserPathCounter.logUserPathEvent(TrialTimerManager.currentTask)
                        if (currentPoints >= item.price) {
                            onPointsUpdated(-item.price)
                            val newOwned = ownedItems + item.name
                            ownedItems = newOwned
                            prefs.edit { putStringSet("owned_items", newOwned) }
                            Toast.makeText(context, "Purchased ${item.name}!", Toast.LENGTH_SHORT).show()

                             // Check and advance trial timer if a trial task is running (tasks 1-4)
                             if (TrialTimerManager.currentTask in 1..4) {
                                 TrialTimerManager.nextTask(context)
                             }
                        }
                    }
                )
            }
            items(bodyItems) { item ->
                ShopItemCard(
                    name = item.name,
                    iconRes = item.resId,
                    price = item.price,
                    isHeadAccessory = false,
                    isOwned = ownedItems.contains(item.name),
                    canAfford = currentPoints >= item.price,
                    onPurchase = {
                        UserPathCounter.logUserPathEvent(TrialTimerManager.currentTask)
                        if (currentPoints >= item.price) {
                            onPointsUpdated(-item.price)
                            val newOwned = ownedItems + item.name
                            ownedItems = newOwned
                            prefs.edit { putStringSet("owned_items", newOwned) }
                            Toast.makeText(context, "Purchased ${item.name}!", Toast.LENGTH_SHORT).show()

                             // Check and advance trial timer if a trial task is running (tasks 1-4)
                             if (TrialTimerManager.currentTask in 1..4) {
                                 TrialTimerManager.nextTask(context)
                             }
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopItemCard(
    name: String,
    iconRes: Int,
    price: Int,
    isHeadAccessory: Boolean,
    isOwned: Boolean,
    canAfford: Boolean,
    onPurchase: () -> Unit
) {
    Card(
        onClick = {
            onPurchase()
            SoundManager.playKachingSound()
        },
        enabled = !isOwned && canAfford,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.yellow_orange),
            disabledContainerColor = colorResource(R.color.yellow_orange).copy(alpha = 0.6f)
        ),
        modifier = Modifier.fillMaxWidth().height(150.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(12.dp)
                .background(colorResource(R.color.dark_yellow_orange)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Content container that takes up available space
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clipToBounds(),
                contentAlignment = Alignment.Center
            ) {
                val alignmentType = if (isHeadAccessory) Alignment.TopCenter else BiasAlignment(
                    horizontalBias = 0f,
                    verticalBias = 0.4f
                )
                val topPaddingType = if (isHeadAccessory) 30.dp else 0.dp

                Image(
                    painter = painterResource(iconRes),
                    contentDescription = name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = topPaddingType)
                        .scale(1.2f),
                    contentScale = ContentScale.Crop,
                    alignment = alignmentType,
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (!isOwned) {
                    Icon(
                        painter = painterResource(R.drawable.ic_paw),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(
                    text = if (isOwned) "Owned" else "$price",
                    color = colorResource(R.color.black),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun ShopScreenPreview() {
    ShopScreen(currentPoints = 100, onPointsUpdated = {})
}
