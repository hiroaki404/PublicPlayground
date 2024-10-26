package com.example.publicplayground

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.media.ExifInterface
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File

@Composable
fun CameraIntentPlayground(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val file = File(context.cacheDir, "IMG.jpg")
    Log.d("file", file.absolutePath) // file scheme path
    val uri =
        FileProvider.getUriForFile(context, "${BuildConfig.APPLICATION_ID}.fileprovider", file)
    Log.d("file", uri.toString()) // content scheme path

    // you can use ActivityResultContracts.TakePicture() instead of StartActivityForResult,
    // if not need to put extra
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, "sample")
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val contentUri = context.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
                )

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) addExif(file)

                context.contentResolver.openOutputStream(contentUri!!).use { output ->
                    file.inputStream().use { input ->
                        input.copyTo(output!!)
                    }
                }
            }
        }

    Column(modifier = modifier) {
        Button(onClick = {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)  // for video
            // you must give content scheme path from FileProvider
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
//            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5)  // restriction for video

            launcher.launch(intent)
        }) {
            Text("Pick Content")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
private fun addExif(file: File) {
    ExifInterface(file).apply {
        // その他の GPS 情報 (オプション)
        setAttribute(ExifInterface.TAG_GPS_LATITUDE, "0/0,0/0000,00000000/00000");
        setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "0");
        setAttribute(ExifInterface.TAG_GPS_LONGITUDE, "0/0,0/0,000000/00000 ");
        setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "0");
        saveAttributes()
    }
}
