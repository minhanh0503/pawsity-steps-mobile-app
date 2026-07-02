package com.example.eecs4443_team7_project.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eecs4443_team7_project.R
import com.example.eecs4443_team7_project.models.Pet
import com.example.eecs4443_team7_project.models.PetColour
import com.example.eecs4443_team7_project.models.PetFace
import com.example.eecs4443_team7_project.models.Track
import com.example.eecs4443_team7_project.models.TrackCategory
import com.example.eecs4443_team7_project.ui.components.TrackerCard

@Composable
fun TrackerScreen(
    pet: Pet,
    onNavigate: (String) -> Unit
) {
    val context = LocalContext.current
    var refresh by remember { mutableIntStateOf(0) }
    val prefs = context.getSharedPreferences("trackers_prefs", Context.MODE_PRIVATE)

    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == androidx.lifecycle.Lifecycle.Event.ON_RESUME) {
                refresh++   // force recomposition
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    val daysAsUser = remember(pet.adoptionDate) {
        val now = System.currentTimeMillis()
        val diff = now - pet.adoptionDate
        (diff / (1000 * 60 * 60 * 24)).toInt()
    }

    val trackerList = remember {
        listOf(
            Track("login", R.string.times_logged_on, TrackCategory.ACHIEVEMENT),
            Track("days", R.string.days_user, TrackCategory.ACHIEVEMENT),
            Track("mood", R.string.times_feeling_logged, TrackCategory.MENTAL),
            Track("affirm", R.string.times_read_affirmation, TrackCategory.MENTAL),
            Track("notice", R.string.times_noticed_something, TrackCategory.MENTAL),
            Track("water", R.string.times_drank_water, TrackCategory.PHYSICAL),
            Track("walk", R.string.times_went_walk, TrackCategory.PHYSICAL),
            Track("stretch", R.string.times_stretched, TrackCategory.PHYSICAL),
            Track("declutter", R.string.times_decluttered, TrackCategory.HABIT),
            Track("message", R.string.times_messaged_friend, TrackCategory.SOCIAL)
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ){
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            items(trackerList) { track ->

                val count = if (track.key == "days") {
                    daysAsUser
                } else {
                    refresh
                    //count walk, water, login
                    prefs.getInt(track.key, 0)
                }

                val text = stringResource(id = track.label, count)

                TrackerCard(
                    track = track,
                    text = text
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TrackerScreenPreview() {
    TrackerScreen(
        pet = Pet("Luna", PetColour.LAVENDER, PetFace.HAPPY, null, null), {})
}