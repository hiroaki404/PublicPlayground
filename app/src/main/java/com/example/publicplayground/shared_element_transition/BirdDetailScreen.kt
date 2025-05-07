package com.example.publicplayground.shared_element_transition

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            Card(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    bird?.let {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(it.imageResId)
                                .crossfade(true)
                                .build(),
                            contentDescription = "image",
                            modifier = Modifier
                                .sharedElement(
                                    sharedContentState = rememberSharedContentState(bird.id),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                )
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
