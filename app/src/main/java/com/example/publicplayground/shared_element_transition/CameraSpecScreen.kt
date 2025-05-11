package com.example.publicplayground.shared_element_transition

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.animateBounds
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentWithReceiverOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.publicplayground.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun CameraSpecScreen(
    expanded: Boolean = false,
    onBack: () -> Unit = {},
    onImageClick: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("カメラスペック") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "戻る"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            LookaheadScope {
                val imageContent = remember {
                    movableContentWithReceiverOf<LookaheadScope, Boolean> { expanded ->
                        AsyncImage(
                            model = R.drawable.camera,
                            contentDescription = "カメラ画像",
                            modifier = Modifier
                                .size(if (expanded) 400.dp else 200.dp)
                                .animateBounds(lookaheadScope = this)
                                .clickable {
                                    onImageClick()
                                }
                        )
                    }
                }

                val cardContent = remember {
                    movableContentWithReceiverOf<LookaheadScope> {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateBounds(lookaheadScope = this),
                        ) {
                            SpecText()
                        }
                    }
                }

                if (expanded) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        imageContent(true)
                        cardContent()
                    }
                } else {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        imageContent(false)
                        cardContent()
                    }
                }
            }
        }
    }
}

@Composable
fun SpecText() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "カメラスペック",
            style = MaterialTheme.typography.titleLarge
        )
        Text("センサーサイズ: 35mmフルサイズ")
        Text("有効画素数: 約4,500万画素")
        Text("ISO感度: 100-51200")
        Text("シャッター速度: 1/8000秒 - 30秒")
        Text("連続撮影: 最大10コマ/秒")
        Text("動画撮影: 4K 60fps")
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
private fun CameraSpecScreenPreview() {
    MaterialTheme {
        LookaheadScope {
            CameraSpecScreen(expanded = false)
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
private fun CameraSpecScreenExpandedPreview() {
    MaterialTheme {
        LookaheadScope {
            CameraSpecScreen(expanded = true)
        }
    }
}
