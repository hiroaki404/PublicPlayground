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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BirdGridScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val birds = listOf(
        Bird(1, "スズメ", "sparrow"),
        Bird(2, "カラス", "crow"),
        Bird(3, "ツバメ", "swallow"),
        Bird(4, "ハト", "pigeon"),
        Bird(5, "ウグイス", "bush_warbler"),
        Bird(6, "メジロ", "white_eye")
    )

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
                        .padding(16.dp)
                ) {
                    Text(text = bird.name)
                }
            }
        }
    }
}

data class Bird(
    val id: Int,
    val name: String,
    val imageResId: String
)
