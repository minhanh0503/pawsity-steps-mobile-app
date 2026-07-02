package com.example.eecs4443_team7_project.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eecs4443_team7_project.R
import com.example.eecs4443_team7_project.models.BodyAccessory
import com.example.eecs4443_team7_project.models.HeadAccessory
import com.example.eecs4443_team7_project.models.Pet
import com.example.eecs4443_team7_project.models.PetColour
import com.example.eecs4443_team7_project.models.PetFace

@Composable
fun PetAvatar(
    pet: Pet,
    modifier: Modifier = Modifier,
    isHeadOnly: Boolean = false
) {
    Box(modifier = modifier.clipToBounds()) {
        val zoomModifier = if (isHeadOnly) {
            Modifier
                .matchParentSize()
                .scale(1.7f) // Zoom in
                .offset(y = 20.dp) // Shift up to focus on head
        } else {
            Modifier.matchParentSize()
        }

        Image(
            painter = painterResource(R.drawable.ic_cat_fill),
            contentDescription = null,
            modifier = zoomModifier,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(
                colorResource(pet.colour.colorRes),
                blendMode = BlendMode.SrcIn
            )
        )

        Image(
            painter = painterResource(R.drawable.ic_cat_outline),
            contentDescription = null,
            modifier = zoomModifier,
            contentScale = ContentScale.Fit
        )

        Image(
            painter = painterResource(pet.face.resId),
            contentDescription = null,
            modifier = zoomModifier,
            contentScale = ContentScale.Fit
        )

        pet.headAccessory?.let {
            Image(
                painter = painterResource(it.resId),
                contentDescription = null,
                modifier = zoomModifier,
                contentScale = ContentScale.Fit
            )
        }

        // Only show body accessory if not in head-only mode
        if (!isHeadOnly) {
            pet.bodyAccessory?.let {
                Image(
                    painter = painterResource(it.resId),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Preview
@Composable
fun PetAvatarPreview() {
    val samplePet = Pet(
        name = "Luna",
        colour = PetColour.FOREST_GREEN,
        face = PetFace.HAPPY,
        headAccessory = HeadAccessory.PROPELLER,
        bodyAccessory = BodyAccessory.BOW
    )

    PetAvatar(samplePet)
}

@Preview
@Composable
fun PetHeadPreview() {
    val samplePet = Pet(
        name = "Luna",
        colour = PetColour.LAVENDER,
        face = PetFace.EXCITED,
        headAccessory = HeadAccessory.WITCH,
        bodyAccessory = BodyAccessory.TIE
    )

    PetAvatar(samplePet, modifier = Modifier.size(100.dp), isHeadOnly = true)
}
