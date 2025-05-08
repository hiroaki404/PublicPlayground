package com.example.publicplayground.shared_element_transition

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ShareElementPlayground(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    SharedTransitionLayout {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = Destination.BirdGrid
        ) {
            composable<Destination.BirdGrid> {
                BirdGridScreen(
                    onBirdClick = { birdId ->
                        navController.navigate(Destination.BirdDetail(birdId))
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable,
                    goToCameraSpec = {
                        navController.navigate(Destination.CameraSpec)
                    }
                )
            }
            composable<Destination.BirdDetail> { backStackEntry ->
                val birdId = backStackEntry.toRoute<Destination.BirdDetail>().birdId
                BirdDetailScreen(
                    birdId = birdId,
                    navigateUp = { navController.navigateUp() },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable
                )
            }

            composable<Destination.CameraSpec> {
                CameraSpecScreen(
                    onBack = { navController.navigateUp() },
                )
            }
        }
    }
}
