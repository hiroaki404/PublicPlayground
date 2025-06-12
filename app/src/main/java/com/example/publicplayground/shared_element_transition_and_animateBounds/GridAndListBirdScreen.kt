package com.example.publicplayground.shared_element_transition_and_animateBounds

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.animateBounds
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.movableContentWithReceiverOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.core.ui.theme.PublicPlaygroundTheme
import com.example.publicplayground.R

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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
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
        val items = remember {
            movableContentWithReceiverOf<LookaheadScope, Boolean> { isGrid ->
                val imageWidth = (LocalConfiguration.current.screenWidthDp - 48) / 2
                birds.forEach { bird ->
                    Row(
                        modifier = Modifier
                            .animateBounds(lookaheadScope = this@movableContentWithReceiverOf)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(bird.imageResId)
                                .build(),
                            contentDescription = bird.name,
                            modifier = Modifier
                                .size(if (isGrid) imageWidth.dp else 128.dp)
                                .animateBounds(lookaheadScope = this@movableContentWithReceiverOf)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        AnimatedVisibility(
                            !isGrid,
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .animateBounds(lookaheadScope = this@movableContentWithReceiverOf)
                            ) {
                                Text(
                                    text = bird.id.toString(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(start = 8.dp),
                                )
                                Text(
                                    text = bird.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(start = 8.dp),
                                )
                            }
                        }
                    }
                }
            }
        }

        LookaheadScope {
            if (isGrid) {
                FlowRow(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(top = 16.dp, start = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    maxItemsInEachRow = 2
                ) {
                    items(isGrid)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(isGrid)
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
