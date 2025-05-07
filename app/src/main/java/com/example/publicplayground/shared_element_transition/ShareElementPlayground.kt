package com.example.publicplayground.shared_element_transition

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun ShareElementPlayground(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "bird_grid"
    ) {
        composable("bird_grid") {
            BirdGridScreen(navController = navController)
        }
        composable(
            route = "bird_detail/{birdId}",
            arguments = listOf(
                navArgument("birdId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val birdId = backStackEntry.arguments?.getInt("birdId") ?: return@composable
            BirdDetailScreen(
                navController = navController,
                birdId = birdId
            )
        }
    }
}
