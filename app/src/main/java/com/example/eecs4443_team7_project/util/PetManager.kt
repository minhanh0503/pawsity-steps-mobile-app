package com.example.eecs4443_team7_project.util

import android.content.Context
import androidx.core.content.edit
import com.example.eecs4443_team7_project.models.BodyAccessory
import com.example.eecs4443_team7_project.models.HeadAccessory
import com.example.eecs4443_team7_project.models.Pet
import com.example.eecs4443_team7_project.models.PetColour
import com.example.eecs4443_team7_project.models.PetFace

/**
 * PetManager, responsible for saving and loading pet data.
 */
object PetManager {
    private const val PET_PREFS = "pet_prefs"
    private const val KEY_NAME = "pet_name"
    private const val KEY_COLOUR = "pet_colour"
    private const val KEY_FACE = "pet_face"
    private const val KEY_HEAD = "pet_head"
    private const val KEY_BODY = "pet_body"
    private const val KEY_ADOPTION_DATE = "pet_adoption_date"

    fun savePet(context: Context, pet: Pet) {
        val prefs = context.getSharedPreferences(PET_PREFS, Context.MODE_PRIVATE)
        prefs.edit {
            putString(KEY_NAME, pet.name)
            putString(KEY_COLOUR, pet.colour.name)
            putString(KEY_FACE, pet.face.name)
            putString(KEY_HEAD, pet.headAccessory?.name)
            putString(KEY_BODY, pet.bodyAccessory?.name)
            putLong(KEY_ADOPTION_DATE, pet.adoptionDate)
        }
    }

    fun loadPet(context: Context): Pet {
        val prefs = context.getSharedPreferences(PET_PREFS, Context.MODE_PRIVATE)

        val name = prefs.getString(KEY_NAME, "your pet") ?: "your pet"

        val colour = try {
            PetColour.valueOf(prefs.getString(KEY_COLOUR, PetColour.LAVENDER.name)!!)
        } catch (e: Exception) {
            PetColour.LAVENDER
        }

        val face = try {
            PetFace.valueOf(prefs.getString(KEY_FACE, PetFace.HAPPY.name)!!)
        } catch (e: Exception) {
            PetFace.HAPPY
        }

        val head = try {
            val headName = prefs.getString(KEY_HEAD, null)
            if (headName != null) HeadAccessory.valueOf(headName) else null
        } catch (e: Exception) {
            null
        }

        val body = try {
            val bodyName = prefs.getString(KEY_BODY, null)
            if (bodyName != null) BodyAccessory.valueOf(bodyName) else null
        } catch (e: Exception) {
            null
        }

        val adoptionDate = prefs.getLong(KEY_ADOPTION_DATE, System.currentTimeMillis())

        return Pet(name, colour, face, head, body, adoptionDate)
    }
}