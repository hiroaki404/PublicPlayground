package com.example.publicplayground.fileandmedia

import android.content.ContentUris
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.io.File

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CustomMoviePicker(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var uris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val thumbnails by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var selectedMovie by remember { mutableStateOf(Uri.EMPTY) }
    var info by remember { mutableStateOf("") }

    LaunchedEffect(selectedMovie) {
        if (selectedMovie == Uri.EMPTY) {
            return@LaunchedEffect
        } else {
            context.contentResolver.openFileDescriptor(selectedMovie, "r")?.use { descriptor ->
                val receiver = MediaMetadataRetriever()
                receiver.setDataSource(descriptor.fileDescriptor)
                receiver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH).let {
                    Log.d("width", "$it")
                }
                receiver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT).let {
                    Log.d("height", "$it")
                }
                receiver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).let {
                    Log.d("duration", "$it")
                }
                Log.d("size", "${descriptor.statSize}")
                receiver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE).let {
                    Log.d("date", "$it")
                }
                receiver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_LOCATION).let {
                    Log.d("location", "$it")
                }
            }

            File(context.cacheDir, "VIDEO.mp4").outputStream().use { output ->
                context.contentResolver.openInputStream(selectedMovie)?.use { input ->
                    input.copyTo(output)
                }
            }
        }
    }

    Column(modifier = modifier) {
        Text(info)

        Button(
            onClick = {
                context.contentResolver.query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    null,
                    null,
                    null,
                    null,
                )?.use { cursor ->
                    val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns._ID)
                    while (cursor.moveToNext()) {
                        val id = cursor.getLong(idColumn)
                        val uri = ContentUris.withAppendedId(
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            id,
                        )
                        uris += uri
                        Log.d("uri", uri.toString())
                    }
                }
            },
        ) {
            Text("Pick Movie with Custom Picker")
        }

        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(top = 16.dp, start = 16.dp, bottom = 60.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            items(items = uris) {
                Text(
                    modifier = Modifier.clickable {
                        selectedMovie = it
                    },
                    text = "$it",
                )
            }
        }
    }
}
