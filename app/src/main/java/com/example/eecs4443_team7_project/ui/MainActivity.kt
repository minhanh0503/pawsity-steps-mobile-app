package com.example.eecs4443_team7_project.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.os.SystemClock
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.edit
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eecs4443_team7_project.R
import com.example.eecs4443_team7_project.models.TelemetryConfig
import com.example.eecs4443_team7_project.models.TelemetrySummary
import com.example.eecs4443_team7_project.ui.navigation.NavigationOption
import com.example.eecs4443_team7_project.ui.navigation.Routes
import com.example.eecs4443_team7_project.ui.screens.ClosetScreen
import com.example.eecs4443_team7_project.ui.screens.CustomizationScreen
import com.example.eecs4443_team7_project.ui.screens.EntryPointScreen
import com.example.eecs4443_team7_project.ui.screens.HomeScreen
import com.example.eecs4443_team7_project.ui.screens.JournalScreen
import com.example.eecs4443_team7_project.ui.screens.QuestsScreen
import com.example.eecs4443_team7_project.ui.screens.SettingsScreen
import com.example.eecs4443_team7_project.ui.screens.ShopScreen
import com.example.eecs4443_team7_project.ui.screens.TelemetryScreen
import com.example.eecs4443_team7_project.ui.screens.TrackerScreen
import com.example.eecs4443_team7_project.ui.theme.EECS4443Team7ProjectTheme
import com.example.eecs4443_team7_project.util.NotificationHelper
import com.example.eecs4443_team7_project.util.PetManager
import com.example.eecs4443_team7_project.util.SoundManager
import com.example.eecs4443_team7_project.util.TrialTimerManager
import com.example.eecs4443_team7_project.util.UserPathCounter

private const val PREFS_NAME = "nav_prefs"
private const val NAV_OPTION_KEY = "nav_option"
private const val POINTS_KEY = "user_points"

/**
 * The flow of MainActivity works as follows:
 *  1. On first launch, user selects UI style (Bottom Bar, Hamburger, Hybrid) via EntryPointScreen.
 *  2. User customizes their pet in CustomizationScreen. Completion is tracked by the customization_complete flag in preferences.
 *  3. After setup, the main app UI is shown using AppScaffold, which delegates to the selected navigation scaffold.
 *      --> Navigation between screens is handled by NavHost and string-based routes.
 */
class MainActivity : ComponentActivity() {
    companion object {
        var processStartTime: Long = SystemClock.elapsedRealtime()
        var firstFrameTime: Long = 0L
    }

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Initialize SoundManager for button click effects using bonk_btn_sound.mp3
        SoundManager.init(this)

        // Notification setup
        NotificationHelper.createNotificationChannel(this)
        NotificationHelper.scheduleDailyNotification(this, 9, 0) // Example: 9:00 AM

        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val musicPrefs = getSharedPreferences("music_prefs", MODE_PRIVATE)
        val savedVolume = musicPrefs.getFloat("bgm_volume", 0.3f)
        mediaPlayer = MediaPlayer.create(this, R.raw.bgm).apply {
            isLooping = true
            setVolume(savedVolume, savedVolume)
            start()
        }
        setContent {
            // Compose state must be inside setContent
            val context = LocalContext.current
            var navOptionOrdinal by remember { mutableIntStateOf(prefs.getInt(NAV_OPTION_KEY, -1)) }
            var customizationComplete by remember { mutableStateOf(prefs.getBoolean("customization_complete", false)) }
            var resetFlow by remember { mutableStateOf(false) }
            var points by remember { mutableIntStateOf(prefs.getInt(POINTS_KEY, 0)) }
            var pet by remember { mutableStateOf(PetManager.loadPet(context)) }
            val navOptions = NavigationOption.entries

            EECS4443Team7ProjectTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val title = if (currentRoute == Routes.HOME || currentRoute?.startsWith(Routes.HOME + "?") == true) {
                    stringResource(R.string.home)
                } else when (currentRoute) {
                    Routes.JOURNAL -> stringResource(R.string.journal)
                    Routes.QUESTS -> stringResource(R.string.quests)
                    Routes.SHOP -> stringResource(R.string.shop)
                    Routes.CLOSET -> stringResource(R.string.closet)
                    Routes.SETTINGS -> stringResource(R.string.settings)
                    Routes.TRACKER -> stringResource(R.string.tracker)
                    Routes.PROFILE -> "Profile"
                    Routes.JOURNAL_ENTRY -> "Journal Entry"
                    else -> stringResource(R.string.app_name)
                }

                if (navOptionOrdinal !in navOptions.indices || resetFlow) {
                    EntryPointScreen(prefs) { newOrdinal ->
                        navOptionOrdinal = newOrdinal
                        customizationComplete = false
                        resetFlow = false
                    }
                } else if (!customizationComplete) {
                    CustomizationScreen(onComplete = { updatedPet ->
                        pet = updatedPet
                        prefs.edit { putBoolean("customization_complete", true) }
                        customizationComplete = true
                    })
                } else {
                    AppScaffold(
                        navType = navOptions[navOptionOrdinal],
                        navController = navController,
                        title = title,
                        points = points
                    ) {
                        LaunchedEffect(customizationComplete) {
                            if (customizationComplete && currentRoute != Routes.HOME) {
                                navController.navigate(Routes.HOME) {
                                    popUpTo(0) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        }
                        NavHost(
                            navController = navController,
                            startDestination = Routes.HOME
                        ) {
                            composable(Routes.CUSTOMIZATION) {
                                CustomizationScreen(onComplete = { updatedPet ->
                                    pet = updatedPet
                                    prefs.edit { putBoolean("customization_complete", true) }
                                    customizationComplete = true
                                    navController.navigate("${Routes.HOME}?fromCustomization=true") {
                                        popUpTo(0) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                })
                            }
                            val updatePoints = { amount: Int ->
                                points += amount
                                prefs.edit { putInt(POINTS_KEY, points) }
                            }
                            composable(
                                "${Routes.HOME}?fromCustomization={fromCustomization}",
                                arguments = listOf(navArgument("fromCustomization") { type = NavType.BoolType; defaultValue = false })
                            ) { backStackEntry ->
                                val fromCustomization = backStackEntry.arguments?.getBoolean("fromCustomization") ?: false
                                HomeScreen(
                                    pet = pet,
                                    onNavigate = { navController.navigate(it) },
                                    fromCustomization = fromCustomization
                                )
                            }
                            composable(Routes.SETTINGS) {
                                SettingsScreen(
                                    onNavigate = { navController.navigate(it) },
                                    onReset = {
                                        navOptionOrdinal = -1
                                        customizationComplete = false
                                        resetFlow = true
                                        navController.navigate("${Routes.HOME}?fromCustomization=true") {
                                            popUpTo(0) { inclusive = true }
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }
                            composable(Routes.JOURNAL) {
                                JournalScreen(
                                    pet = pet,
                                    onNavigate = { navController.navigate(it) }
                                )
                            }
                             composable(Routes.QUESTS) {
                                 QuestsScreen(
                                     onPointsUpdated = updatePoints
                                 )
                             }
                            composable(Routes.SHOP) {
                                ShopScreen(
                                    currentPoints = points,
                                    onPointsUpdated = updatePoints
                                )
                            }
                            composable(Routes.CLOSET) {
                                ClosetScreen(
                                    currentPet = pet,
                                    onPetChanged = { newPet ->
                                        pet = newPet
                                        PetManager.savePet(context, newPet)
                                    },
                                    onNavigate = { navController.navigate(it) }
                                )
                            }
                            composable(Routes.TELEMETRY) {
                                val context = LocalContext.current
                                val telemetryPrefs = context.getSharedPreferences("telemetry_prefs", MODE_PRIVATE)
                                val participantName = telemetryPrefs.getString("participant", "") ?: ""
                                val navType = when (navOptionOrdinal) {
                                    0 -> "A"
                                    1 -> "B"
                                    2 -> "C"
                                    else -> "?"
                                }
                                val times = TrialTimerManager.getTimes().map { it / 1000.0 }
                                val optimalPathCounts = TelemetryConfig.getOptimalPath(navType)
                                val userPathCounts = UserPathCounter.getCounts()
                                // Absolute value of (fewest clicks - total user clicks)
                                val errorRates = optimalPathCounts.zip(userPathCounts) { opt, user -> kotlin.math.abs(opt - user) }
                                val batteryLevels = TrialTimerManager.getBatteryLevels()
                                val summary = TelemetrySummary(
                                    participantName = participantName,
                                    uiType = navType,
                                    timePerTask = times,
                                    errorRatePerTask = errorRates,
                                    batteryPerTask = batteryLevels
                                )
                                TelemetryScreen(summary) { navController.navigate(it) }
                            }
                            composable(Routes.TRACKER) {
                                TrackerScreen(
                                    pet = pet,
                                    onNavigate = { navController.navigate(it) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun setMusicVolume(volume: Float) {
        mediaPlayer?.setVolume(volume, volume)
    }

    override fun onStart() {
        super.onStart()
        mediaPlayer?.start()
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        SoundManager.release()
    }
}