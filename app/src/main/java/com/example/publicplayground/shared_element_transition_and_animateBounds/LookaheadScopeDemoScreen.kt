package com.example.publicplayground.shared_element_transition_and_animateBounds

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// refer to
// https://developer.android.com/reference/kotlin/androidx/compose/ui/layout/LookaheadScope

@Composable
fun LookaheadScopeDemoScreen() {
    LookaheadScopeDemoScreenContent()
}

@Composable
fun LookaheadScopeDemoScreenContent(
    modifier: Modifier = Modifier,
) {
    val colors = listOf(
        Color(0xFFBB86FC),
        Color(0xFF6200EE),
        Color(0xFF3700B3),
        Color(0xFF03DAC5)
    )

    var isInColumn by remember { mutableStateOf(false) }
    LookaheadScope {
        val items = remember {
            movableContentOf {
                colors.forEachIndexed { index, color ->
                    Surface(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(50.dp)
                            .animatePlacement(this),
                        color = color
                    ) {}
                }
            }
        }

        Scaffold { innerPadding ->
            Column(modifier = modifier.padding(innerPadding)) {
                Button(onClick = { isInColumn = !isInColumn }) {
                    Text(text = "Toggle Layout")
                }
                Spacer(modifier = Modifier.size(16.dp))
                if (isInColumn) {
                    Column {
                        items()
                    }
                } else {
                    Row {
                        items()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LookaheadScopeDemoScreenPreview() {
    LookaheadScopeDemoScreenContent()
}

