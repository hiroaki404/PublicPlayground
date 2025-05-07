package com.example.publicplayground.shared_element_transition

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

@Composable
fun ShareElementPlayground(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destination.BirdGrid
    ) {
        composable<Destination.BirdGrid> {
            BirdGridScreen(navController = navController)
        }
        composable<Destination.BirdDetail> { backStackEntry ->
            val birdId = backStackEntry.toRoute<Destination.BirdDetail>().birdId
            BirdDetailScreen(
                navController = navController,
                birdId = birdId
            )
        }
    }
}
