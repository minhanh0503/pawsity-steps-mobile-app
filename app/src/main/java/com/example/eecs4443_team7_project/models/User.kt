
package com.example.eecs4443_team7_project.models

/**
 * NOTE: This User data class is not used in the app, as all user-related data is handled via SharedPreferences
 * for research and study design reasons. This file is kept for archival and testing purposes only.
 */
data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val preferences: Map<String, String> = emptyMap()
)
