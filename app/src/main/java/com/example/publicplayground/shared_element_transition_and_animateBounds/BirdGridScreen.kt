package com.example.publicplayground.shared_element_transition_and_animateBounds

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.publicplayground.R

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BirdGridScreen(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onBirdClick: (Int) -> Unit = {},
    goToCameraSpec: () -> Unit = {},
    goToQuiz: () -> Unit = {},
) {
    with(sharedTransitionScope) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = {
                        Text("Playground")
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(birds) { bird ->
                        val useSharedElement = bird.id % 2 == 1
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Gray.copy(alpha = 0.2f))
                                .run {
                                    if (useSharedElement) {
                                        this
                                    } else {
                                        this.sharedBounds(
                                            sharedContentState = rememberSharedContentState(bird.id),
                                            animatedVisibilityScope = animatedVisibilityScope,
                                            enter = fadeIn(tween(2_000)),
                                            exit = fadeOut(tween(2_000)),
                                            resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                                        )
                                    }
                                }
                                .clickable { onBirdClick(bird.id) }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(bird.imageResId)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "image",
                                    modifier = Modifier
                                        .run {
                                            if (useSharedElement) {
                                                this.sharedElement(
                                                    sharedContentState = rememberSharedContentState(
                                                        bird.id
                                                    ),
                                                    animatedVisibilityScope = animatedVisibilityScope,
                                                )
                                            } else {
                                                this
                                            }
                                        }
                                        .weight(1f)
                                        .fillMaxWidth(),
                                )
                                Text(
                                    text = bird.name,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    }
                }
                Button(onClick = goToCameraSpec) {
                    Text(stringResource(R.string.camera_spec_button))
                }
                Button(onClick = goToQuiz) {
                    Text(stringResource(id = R.string.quiz_navigation_button))
                }
                Text("AI generated images for the images.")
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
private fun BirdGridScreenPreview() {
    SharedTransitionLayout {
        AnimatedVisibility(visible = true) {
            BirdGridScreen(
                sharedTransitionScope = this@SharedTransitionLayout,
                animatedVisibilityScope = this@AnimatedVisibility
            )
        }
    }
}
