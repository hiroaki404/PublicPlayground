package com.example.publicplayground.fileandmedia

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
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
fun PhotoPickerPlayground(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            requireNotNull(uri) { return@rememberLauncherForActivityResult }
            // exifを読めるなら読んでログに出力する
            context.contentResolver.openFileDescriptor(uri, "r")?.use { descriptor ->
                readExif(descriptor.fileDescriptor)
            }

            // uriからmimeTypeを取得
            val suffix = context.contentResolver.getType(uri)?.let {
                Log.d("type", it)
                when {
                    it.startsWith("image/jpeg") -> ".jpg"
                    it.startsWith("image/png") -> ".png"
                    it.startsWith("image/gif") -> ".gif"
                    it.startsWith("image/bmp") -> ".bmp"
                    it.startsWith("image/webp") -> ".webp"
                    it.startsWith("image/heic") -> ".heic"
                    it.startsWith("image/tiff") -> ".tiff"
                    else -> ".bmp"
                }
            }

            val file = File(context.filesDir, "IMG${suffix}")

            context.contentResolver.openInputStream(uri).use { input ->
                // 内部ストレージにコピー
                file.outputStream().use { output ->
                    input?.copyTo(output)
                }

                // 内部ストレージから読み込んで、画面幅やサイズを得る
                // 圧縮可能なものは圧縮する
                file.inputStream().use { input ->
                    if (listOf(".jpg", ".png", ".bmp", "webp", "gif").contains(suffix)) {
                        val bitmap = BitmapFactory.decodeStream(input)
                        Log.d("bitmap", "${bitmap.width} x ${bitmap.height}")
                        Log.d("bitmap", "${bitmap.rowBytes}")

                        if (listOf(".jpg", ".png", ".bmp", "webp").contains(suffix)) {
                            val compressFile = File(context.filesDir, "compress.jpg")
                            compressFile.outputStream().use { output ->
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, output)
                            }

                            // 圧縮したものを読み込んで、画面幅やサイズを得る
                            compressFile.inputStream().use { input ->
                                val compressBitmap = BitmapFactory.decodeStream(input)
                                Log.d(
                                    "compress bitmap",
                                    "${compressBitmap.width} x ${compressBitmap.height}"
                                )
                                Log.d("compress bitmap", "${compressBitmap.rowBytes}")
                            }
                        }
                    }
                }

            }
        }

    Column(modifier = modifier) {
        Button(onClick = {
            val request = PickVisualMediaRequest.Builder()
//                .setMediaType(ActivityResultContracts.PickVisualMedia.SingleMimeType("image/jpeg")) // jpgだけの場合
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                .build()

            launcher.launch(request)
        }) {
            Text("Pick Photo")
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
