package com.example.eecs4443_team7_project.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.eecs4443_team7_project.R
import com.example.eecs4443_team7_project.ui.navigation.Routes
import com.example.eecs4443_team7_project.util.TrialTimerManager
import com.example.eecs4443_team7_project.util.UserPathCounter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarScaffold(
    navController: NavController,
    title: String,
    points: Int = 0,
    content: @Composable () -> Unit
) {
    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.black)
                    ) },
                actions = {
                    PointsDisplay(points = points, modifier = Modifier.padding(end = 8.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.matcha_green)
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = colorResource(R.color.matcha_green),
            ) {
                NavigationBarItem(
                    selected = currentRoute == Routes.HOME,
                    onClick = {
                        UserPathCounter.logUserPathEvent(TrialTimerManager.currentTask)
                        navController.navigate(Routes.HOME)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = colorResource(R.color.dark_matcha_green),
                        selectedIconColor = colorResource(R.color.black),
                        selectedTextColor = colorResource(R.color.black),
                        unselectedIconColor = colorResource(R.color.black).copy(alpha = 0.7f),
                        unselectedTextColor = colorResource(R.color.black).copy(alpha = 0.7f)
                    ),
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_home),
                            contentDescription = stringResource(R.string.home),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text(stringResource(R.string.home)) }
                )

                NavigationBarItem(
                    selected = currentRoute == Routes.CLOSET,
                    onClick = {
                        UserPathCounter.logUserPathEvent(TrialTimerManager.currentTask)
                        navController.navigate(Routes.CLOSET)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = colorResource(R.color.dark_matcha_green),
                        selectedIconColor = colorResource(R.color.black),
                        selectedTextColor = colorResource(R.color.black),
                        unselectedIconColor = colorResource(R.color.black).copy(alpha = 0.7f),
                        unselectedTextColor = colorResource(R.color.black).copy(alpha = 0.7f)
                    ),
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_closet),
                            contentDescription = stringResource(R.string.closet),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text(stringResource(R.string.closet)) }
                )

                NavigationBarItem(
                    selected = currentRoute == Routes.JOURNAL,
                    onClick = {
                        UserPathCounter.logUserPathEvent(TrialTimerManager.currentTask)
                        navController.navigate(Routes.JOURNAL)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = colorResource(R.color.dark_matcha_green),
                        selectedIconColor = colorResource(R.color.black),
                        selectedTextColor = colorResource(R.color.black),
                        unselectedIconColor = colorResource(R.color.black).copy(alpha = 0.7f),
                        unselectedTextColor = colorResource(R.color.black).copy(alpha = 0.7f)
                    ),
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_journal),
                            contentDescription = stringResource(R.string.journal),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text(stringResource(R.string.journal)) }
                )

                NavigationBarItem(
                    selected = currentRoute == Routes.QUESTS,
                    onClick = {
                        UserPathCounter.logUserPathEvent(TrialTimerManager.currentTask)
                        navController.navigate(Routes.QUESTS)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = colorResource(R.color.dark_matcha_green),
                        selectedIconColor = colorResource(R.color.black),
                        selectedTextColor = colorResource(R.color.black),
                        unselectedIconColor = colorResource(R.color.black).copy(alpha = 0.7f),
                        unselectedTextColor = colorResource(R.color.black).copy(alpha = 0.7f)
                    ),
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_quests),
                            contentDescription = stringResource(R.string.quests),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text(stringResource(R.string.quests)) }
                )

                NavigationBarItem(
                    selected = currentRoute == Routes.SHOP,
                    onClick = {
                        UserPathCounter.logUserPathEvent(TrialTimerManager.currentTask)
                        navController.navigate(Routes.SHOP)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = colorResource(R.color.dark_matcha_green),
                        selectedIconColor = colorResource(R.color.black),
                        selectedTextColor = colorResource(R.color.black),
                        unselectedIconColor = colorResource(R.color.black).copy(alpha = 0.7f),
                        unselectedTextColor = colorResource(R.color.black).copy(alpha = 0.7f)
                    ),
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_shop),
                            contentDescription = stringResource(R.string.shop),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text(stringResource(R.string.shop)) }
                )

            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            content()
        }
    }
}

@Preview (showBackground = true)
@Composable
fun BottomBarScaffoldPreview() {
    val navController = rememberNavController()
    BottomBarScaffold(navController, "Home") { }
}
