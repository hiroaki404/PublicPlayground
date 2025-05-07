package com.example.publicplayground.shared_element_transition

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun BirdDetailScreen(
    birdId: Int,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigateUp: () -> Unit = {},
) {
    val bird = birds.find { it.id == birdId }
    with(sharedTransitionScope) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = { Text(text = bird?.name ?: "鳥の詳細") },
                    navigationIcon = {
                        IconButton(onClick = navigateUp) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            bird?.let {
                val useSharedElement = bird.id % 2 == 1
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Blue.copy(alpha = 0.2f))
                        .run {
                            if (useSharedElement) {
                                this
                            } else {
                                this.sharedBounds(
                                    sharedContentState = rememberSharedContentState(it.id),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    enter = fadeIn(tween(2_000)),
                                    exit = fadeOut(tween(2_000)),
                                    resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                                )
                            }
                        }
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (useSharedElement) {
                                "use shared element"
                            } else {
                                "use shared bounds"
                            },
                        )
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(it.imageResId)
                                .crossfade(true)
                                .build(),
                            contentDescription = "image",
                            modifier = Modifier
                                .run {
                                    if (useSharedElement) {
                                        this.sharedElement(
                                            sharedContentState = rememberSharedContentState(it.id),
                                            animatedVisibilityScope = animatedVisibilityScope,
                                        )
                                    } else {
                                        this
                                    }
                                }
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "ID: ${it.id}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "名前: ${it.name}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "画像リソース: ${it.imageResId}")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
private fun BirdDetailScreenPreview() {
    SharedTransitionLayout {
        AnimatedVisibility(visible = true) {
            BirdDetailScreen(
                birdId = 1,
                sharedTransitionScope = this@SharedTransitionLayout,
                animatedVisibilityScope = this@AnimatedVisibility
            )
        }
    }
}
