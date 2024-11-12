package com.example.publicplayground.fileandmedia

import android.media.ExifInterface
import android.media.MediaMetadataRetriever
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.io.File
import java.io.FileDescriptor

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MoviePickerPlayground(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            requireNotNull(uri) { return@rememberLauncherForActivityResult }
            // exifを読めるなら読んでログに出力する
            context.contentResolver.openFileDescriptor(uri!!, "r")?.use { descriptor ->
                readExif(descriptor.fileDescriptor)
            }

            // uriからmimeTypeを取得
            val suffix = context.contentResolver.getType(uri)?.let {
                Log.d("type", it)
                when {
                    it.startsWith("video/mp4") -> ".mp4"
                    else -> ".mp4"
                }
            }

            val file = File(context.filesDir, "VIDEO${suffix}")

            context.contentResolver.openInputStream(uri).use { input ->
                // 内部ストレージにコピー
                file.outputStream().use { output ->
                    input?.copyTo(output)
                }
            }

            val receiver = MediaMetadataRetriever()
            receiver.setDataSource(file.path)
            receiver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH).let {
                Log.d("width", "${it}")
            }
            receiver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT).let {
                Log.d("height", "${it}")
            }
            receiver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).let {
                Log.d("duration", "${it}")
            }
            Log.d("file", "${file.length()}")
            receiver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE).let {
                Log.d("date", "${it}")
            }
            receiver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_LOCATION).let {
                Log.d("location", "${it}")
            }
        }

    Column(modifier = modifier) {
        Button(onClick = {
            val request = PickVisualMediaRequest.Builder()
//                .setMediaType(ActivityResultContracts.PickVisualMedia.SingleMimeType("image/jpeg")) // jpgだけの場合
                .setMediaType(ActivityResultContracts.PickVisualMedia.VideoOnly)
                .build()

            launcher.launch(request)
        }) {
            Text("Pick Movie")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
private fun readExif(file: FileDescriptor) {
    ExifInterface(file).apply {
        getAttribute(ExifInterface.TAG_DATETIME)?.let {
            Log.d("datetime", it)
        }
        getAttribute(ExifInterface.TAG_GPS_LATITUDE).let {
            Log.d("latitude", it.toString())
        }

    }
}

@RequiresApi(Build.VERSION_CODES.Q)
private fun addExif(file: File) {
    ExifInterface(file).apply {
        // その他の GPS 情報 (オプション)
        setAttribute(ExifInterface.TAG_DATETIME, "2021:09:01 12:00:00");
        saveAttributes()
    }
}
