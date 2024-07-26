package de.thkoeln.modi.multibezel.ui.layout

import androidx.compose.runtime.Composable
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController

@Composable
fun AppComposable() {
    val swipeDismissibleNavController = rememberSwipeDismissableNavController()

    SwipeDismissableNavHost(
        navController = swipeDismissibleNavController, startDestination = Screen.Welcome.route
    ) {
        composable(route = Screen.Welcome.route) {
            Welcome(
                navigateMusicPlayer = { swipeDismissibleNavController.navigate(Screen.MusicPlayer.route){
                    popUpTo(0)
                } },
                navigateRawData = {swipeDismissibleNavController.navigate(Screen.RawData.route)}
                )
        }
        composable(route = Screen.MusicPlayer.route) {
            CustomMusicPlayerScreen(
                navigateBack = { swipeDismissibleNavController.navigate(Screen.Welcome.route) }
            )
        }
        composable(route = Screen.RawData.route) {
            SensorSections()
        }
    }
}

sealed class Screen(
    val route: String
) {
    data object Welcome : Screen("welcome")
    data object MusicPlayer : Screen("player")
    data object RawData : Screen("rawdata")
}