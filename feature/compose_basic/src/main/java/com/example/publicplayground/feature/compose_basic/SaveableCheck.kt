package com.example.publicplayground.feature.compose_basic

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun SaveableCheck(modifier: Modifier = Modifier) {
    var key: Int by remember { mutableIntStateOf(1) }
    val value: Int? by rememberSaveable(key, key = key.toString()) { mutableIntStateOf(key) }

    LaunchedEffect(Unit) {
        key = 1
        Log.d("SavableCheck", "LaunchedEffect $key")
        delay(3000)
        key = 2
    }

    Column(modifier = modifier) {
        Text("Value: $value")
        Button(onClick = { key = 2 }) {
            Text("button")
        }
    }
}
