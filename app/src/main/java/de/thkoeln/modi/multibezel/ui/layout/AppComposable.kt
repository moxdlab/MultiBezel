package de.thkoeln.modi.multibezel.ui.layout

import androidx.compose.runtime.Composable
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController

@Composable
fun AppComposable(){
    val swipeDismissibleNavController = rememberSwipeDismissableNavController()

    SwipeDismissableNavHost(
        navController = swipeDismissibleNavController,
        startDestination = Screen.MusicPlayer.route
    ) {
        composable(route = Screen.Welcome.route){
            Welcome()
        }
        composable(route = Screen.MusicPlayer.route){
            MusicPlayer()
        }
    }
}

sealed class Screen(
    val route: String
) {
    data object Welcome: Screen("welcome")
    data object MusicPlayer: Screen("player")
}