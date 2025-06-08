package com.example.publicplayground.shared_element_transition_and_animateBounds

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.publicplayground.R
import com.example.publicplayground.ui.theme.PublicPlaygroundTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridAndListBirdScreen(modifier: Modifier = Modifier) {
    var isGrid by remember { mutableStateOf(true) }
    GridAndListBirdScreenContent(
        isGrid = isGrid,
        onGridClick = { isGrid = true },
        onListClick = { isGrid = false },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridAndListBirdScreenContent(
    isGrid: Boolean,
    onGridClick: () -> Unit,
    onListClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Grid And List Bird") },
                actions = {
                    IconButton(onClick = onGridClick) {
                        val gridBgColor by animateColorAsState(if (isGrid) MaterialTheme.colorScheme.primary else Color.Transparent)
                        val gridIconColor by animateColorAsState(if (isGrid) Color.White else Color.Gray)
                        Surface(
                            shape = CircleShape,
                            color = gridBgColor
                        ) {
                            Icon(
                                modifier = Modifier.padding(8.dp),
                                painter = painterResource(id = R.drawable.ic_grid),
                                contentDescription = "Grid",
                                tint = gridIconColor
                            )
                        }
                    }
                    IconButton(onClick = onListClick) {
                        val listBgColor by animateColorAsState(if (!isGrid) MaterialTheme.colorScheme.primary else Color.Transparent)
                        val listIconColor by animateColorAsState(if (!isGrid) Color.White else Color.Gray)
                        Surface(
                            shape = CircleShape,
                            color = listBgColor
                        ) {
                            Icon(
                                modifier = Modifier.padding(8.dp),
                                imageVector = Icons.Filled.List,
                                contentDescription = "List",
                                tint = listIconColor
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        if (isGrid) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
            ) {
                items(birds) { bird ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(bird.imageResId)
                            .crossfade(true)
                            .build(),
                        contentDescription = bird.name,
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Gray.copy(alpha = 0.2f))
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
            ) {
                items(birds) { bird ->
                    Row(modifier = Modifier.fillMaxWidth().background(Color.Gray.copy(alpha = 0.2f))) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(bird.imageResId)
                                .crossfade(true)
                                .build(),
                            contentDescription = bird.name,
                            modifier = Modifier
                                .size(128.dp)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Gray.copy(alpha = 0.2f))
                        )
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = bird.id.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                            Text(
                                text = bird.name,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GridAndListBirdScreenPreview_Grid() {
    PublicPlaygroundTheme {
        GridAndListBirdScreenContent(
            isGrid = true,
            onGridClick = {},
            onListClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GridAndListBirdScreenPreview_List() {
    PublicPlaygroundTheme {
        GridAndListBirdScreenContent(
            isGrid = false,
            onGridClick = {},
            onListClick = {}
        )
    }
}
