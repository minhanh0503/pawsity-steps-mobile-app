package com.example.eecs4443_team7_project.models

import androidx.annotation.ColorRes
import com.example.eecs4443_team7_project.R

data class Pet(
    val name: String,
    val colour: PetColour,
    val face: PetFace,
    val headAccessory: HeadAccessory?,
    val bodyAccessory: BodyAccessory?,
    val adoptionDate: Long = System.currentTimeMillis()
)

enum class PetColour(@ColorRes val colorRes: Int) {
    BLUSH_PINK(R.color.blush_pink),
    LAVENDER(R.color.lavender),
    PEACH(R.color.peach),
    CREAM(R.color.cream),
    FOREST_GREEN(R.color.forest_green),
    DUSTY_BROWN(R.color.dusty_brown),
    OLIVE(R.color.olive),
    SLATE_BLUE(R.color.slate_blue),
}

enum class PetFace(val resId: Int) {
    HAPPY(R.drawable.ic_cat_face_1),
    SILLY(R.drawable.ic_cat_face_2),
    SMIRK(R.drawable.ic_cat_face_3),
    EXCITED(R.drawable.ic_cat_face_4),
}

enum class HeadAccessory(val resId: Int, val price: Int) {
    WITCH(R.drawable.ic_witch, 15),
    CROWN(R.drawable.ic_crown, 40),
    PROPELLER(R.drawable.ic_hat, 20),
}

enum class BodyAccessory(val resId: Int, val price: Int) {
    PEARLS(R.drawable.ic_pearls, 30),
    BOW(R.drawable.ic_bow, 10),
    TIE(R.drawable.ic_tie, 10),
}