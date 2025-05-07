package com.example.publicplayground.shared_element_transition

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun BirdGridScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(birds) { bird ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clickable {
                        navController.navigate(Destination.BirdDetail(bird.id))
                    }
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
}
