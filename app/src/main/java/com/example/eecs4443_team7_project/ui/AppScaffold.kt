package com.example.eecs4443_team7_project.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eecs4443_team7_project.R
import com.example.eecs4443_team7_project.ui.components.BottomBarScaffold
import com.example.eecs4443_team7_project.ui.components.HamburgerDrawerScaffold
import com.example.eecs4443_team7_project.ui.components.HybridScaffold
import com.example.eecs4443_team7_project.ui.navigation.NavigationOption

/**
 * Blueprint for the main app scaffold.
 * This class is responsible for the main app scaffold.
 * Delegates to selected navigation scaffold based on the navType parameter.
 *
 */

@Composable
fun AppScaffold(
    navType: NavigationOption,
    navController: NavController,
    title: String,
    points: Int = 0,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.sea_green))
    ) {
        
        Image(
            painter = painterResource(R.drawable.trail_graphic),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 220.dp),
            contentScale = ContentScale.FillWidth
        )
        
        when (navType) {
            NavigationOption.BOTTOM_BAR -> BottomBarScaffold(navController, title, points, content)
            NavigationOption.HAMBURGER -> HamburgerDrawerScaffold(navController, title, points, content)
            NavigationOption.HYBRID -> HybridScaffold(navController, title, points, content)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppScaffoldPreview() {
    val navController = rememberNavController()
    AppScaffold(navType = NavigationOption.HAMBURGER, navController, stringResource(R.string.home)) { }
}
