package com.example.publicplayground.fileandmedia

import android.content.ContentUris
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import java.io.File
import java.io.FileDescriptor
import java.io.InputStream

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CustomPhotoPicker(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var uris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var selectedImage by remember { mutableStateOf(Uri.EMPTY) }
    var info by remember { mutableStateOf("") }

    LaunchedEffect(selectedImage) {
        if (selectedImage == Uri.EMPTY) {
            return@LaunchedEffect
        } else {
            context.contentResolver.openFileDescriptor(selectedImage, "r")?.use { descriptor ->
                info = readExif(descriptor.fileDescriptor)
            }

            File(context.cacheDir, "IMG.jpg").outputStream().use { output ->
                context.contentResolver.openInputStream(selectedImage)?.use { input ->
                    readExif(input)
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
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null,
                    null,
                    null,
                    null,
                )?.use { cursor ->
                    val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                    while (cursor.moveToNext()) {
                        val id = cursor.getLong(idColumn)
                        val uri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            id,
                        )
                        uris += uri
                        Log.d("uri", uri.toString())
                    }
                }
            },
        ) {
            Text("Pick Photo with Custom Picker")
        }

        if (uris.isNotEmpty()) {
            LazyVerticalGrid(
                modifier = modifier.height(400.dp),
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 16.dp,
                    bottom = 60.dp,
                    end = 16.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                items(items = uris) {
                    AsyncImage(
                        modifier = Modifier
                            .size(100.dp)
                            .clickable {
                                selectedImage = it
                            },
                        model = it,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
private fun readExif(file: FileDescriptor): String {
    var info: String = ""
    ExifInterface(file).apply {
        getAttribute(ExifInterface.TAG_DATETIME)?.let {
            Log.d("datetime", it)
            info += it
        }
        getAttribute(ExifInterface.TAG_GPS_LATITUDE).let {
            Log.d("latitude", it.toString())
            info += it.toString()
        }
        getAttribute(ExifInterface.TAG_GPS_DEST_LATITUDE).let {
            Log.d("latitude", it.toString())
            info += it
        }
    }
    return info
}

@RequiresApi(Build.VERSION_CODES.Q)
private fun readExif(file: InputStream): String {
    var info: String = ""
    ExifInterface(file).apply {
        getAttribute(ExifInterface.TAG_DATETIME)?.let {
            Log.d("datetime", it)
            info += it
        }
        getAttribute(ExifInterface.TAG_GPS_LATITUDE).let {
            Log.d("latitude", it.toString())
            info += it.toString()
        }
        getAttribute(ExifInterface.TAG_GPS_DEST_LATITUDE).let {
            Log.d("latitude", it.toString())
            info += it
        }
    }
    return info
}
