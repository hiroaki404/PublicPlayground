package com.example.publicplayground.shared_element_transition

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirdDetailScreen(
    navController: NavController,
    birdId: Int,
    modifier: Modifier = Modifier
) {
    val bird = birds.find { it.id == birdId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = bird?.name ?: "鳥の詳細") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        // TODO: 戻るボタンのアイコンを追加
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
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
