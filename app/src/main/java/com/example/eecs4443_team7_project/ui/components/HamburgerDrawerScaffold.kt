package com.example.eecs4443_team7_project.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eecs4443_team7_project.R
import com.example.eecs4443_team7_project.ui.navigation.Routes
import com.example.eecs4443_team7_project.util.TrialTimerManager
import com.example.eecs4443_team7_project.util.UserPathCounter
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HamburgerDrawerScaffold(
    navController: NavController,
    title: String,
    points: Int = 0,
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(modifier = Modifier
                .fillMaxHeight()
                ) {
                ModalDrawerSheet(
                    modifier = Modifier
                        .requiredWidth(IntrinsicSize.Min)
                        .wrapContentHeight(),
                    drawerShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
                    drawerContainerColor = colorResource(R.color.matcha_green)
                ) {
                    DrawerContent(
                        onItemClick = { route ->
                            UserPathCounter.logUserPathEvent(TrialTimerManager.currentTask)
                            scope.launch { drawerState.close() }
                            navController.navigate(route)
                        }
                    )
                }
            }
        }
    ) {
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
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                UserPathCounter.logUserPathEvent(TrialTimerManager.currentTask)
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = stringResource(R.string.menu),
                                tint = colorResource(R.color.black))
                        }
                    },
                    actions = {
                        PointsDisplay(points = points, modifier = Modifier.padding(end = 8.dp))
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(R.color.matcha_green)
                    )
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                content()
            }
        }
    }
}

@Composable
fun DrawerContent(
    onItemClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        DrawerItem(
            stringResource(R.string.home),
            icon = painterResource(R.drawable.ic_home)
        ) { onItemClick(Routes.HOME) }

        DrawerItem(
            stringResource(R.string.quests),
            icon = painterResource(R.drawable.ic_quests)
        ) { onItemClick(Routes.QUESTS) }

        DrawerItem(
            stringResource(R.string.shop),
            icon = painterResource(R.drawable.ic_shop)
        ) { onItemClick(Routes.SHOP) }

        DrawerItem(
            stringResource(R.string.closet),
            icon = painterResource(R.drawable.ic_closet)
        ) { onItemClick(Routes.CLOSET) }

        DrawerItem(
            stringResource(R.string.journal),
            icon = painterResource(R.drawable.ic_journal)
        ) { onItemClick(Routes.JOURNAL) }

        DrawerItem(
            stringResource(R.string.tracker),
            icon = painterResource(R.drawable.ic_tracker)
        ) { onItemClick(Routes.TRACKER) }
        DrawerItem(
            stringResource(R.string.settings),
            icon = painterResource(R.drawable.ic_gear)
        ) { onItemClick(Routes.SETTINGS) }
    }
}

@Composable
fun DrawerItem(
    label: String,
    icon: Painter,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.textButtonColors(
            contentColor = colorResource(R.color.black)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = icon,
                contentDescription = label,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = label,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview
@Composable
fun HamburgerDrawerScaffoldPreview() {
    val navController = rememberNavController()
    HamburgerDrawerScaffold(navController, stringResource(R.string.home)) { }
}
