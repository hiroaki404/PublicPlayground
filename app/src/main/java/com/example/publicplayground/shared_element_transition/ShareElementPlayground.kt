package com.example.publicplayground.shared_element_transition

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
                    },
                    goToQuiz = {
                        navController.navigate(Destination.Quiz)
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
                var expanded by remember { mutableStateOf(false) }
                CameraSpecScreen(
                    onBack = { navController.navigateUp() },
                    expanded = expanded,
                    onImageClick = {
                        expanded = !expanded
                    }
                )
            }

            composable<Destination.Quiz> {
                var expanded by remember { mutableStateOf(false) }
                QuizScreen(
                    expanded = expanded,
                    onBack = { navController.navigateUp() },
                    onButtonClick = {
                        expanded = !expanded
                    }
                )
            }
        }
    }
}
