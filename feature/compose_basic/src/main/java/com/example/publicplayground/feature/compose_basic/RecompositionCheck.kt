package com.example.publicplayground.feature.compose_basic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RecompositionCheck(modifier: Modifier = Modifier) {
    val bird = remember { PlainBird() } // need to remember the state

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text(bird.state.name)

        Button(onClick = bird::fly) {
            Text("Fly")
        }

        Button(onClick = bird::rest) {
            Text("Rest")
        }
    }
}

class PlainBird {
    var state: BirdState by mutableStateOf(BirdState.Resting)
        private set

    fun fly() {
        state = BirdState.Flying
    }

    fun rest() {
        state = BirdState.Resting
    }
}

enum class BirdState {
    Flying, Resting
}

@Preview
@Composable
private fun RecompositionCheckPreview() {
    RecompositionCheck()
}
