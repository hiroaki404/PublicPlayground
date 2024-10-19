package com.example.publicplayground

import android.app.Activity.MODE_PRIVATE
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.res.imageResource
import java.io.File
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer

// c.bmpにresourceの画像を保存する
fun Context.filePlayground() {
    val file = File(filesDir, "c.bmp") // ファイル
    Log.d("test", file.path)

    val imageBitmap = ImageBitmap.imageResource(resources, R.drawable.dots)
    val bitmap = imageBitmap.asAndroidBitmap()

    openFileOutput(file.name, MODE_PRIVATE).use {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
    }
}

// d.bmpにresourceの画像を保存する
fun Context.okioPlayground() {
    val fileSource = FileSystem.SYSTEM.sink("${filesDir.path}/d.bmp".toPath())

    val imageBitmap = ImageBitmap.imageResource(resources, R.drawable.dots)
    val bitmap = imageBitmap.asAndroidBitmap()

    fileSource.use {
        it.buffer().use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it.outputStream())
        }
    }
}
