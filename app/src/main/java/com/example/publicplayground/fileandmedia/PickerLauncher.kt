package com.example.publicplayground.fileandmedia

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@SuppressLint("NewApi")
@Composable
fun PickerLauncher(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CameraIntentPlayground()
        PhotoPickerPlayground()
        VideoIntentPlayground()
        MoviePickerPlayground()
    }
}
