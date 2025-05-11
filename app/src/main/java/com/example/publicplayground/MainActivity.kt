package com.example.publicplayground

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.publicplayground.feature.workmanager.SimpleCoroutineWorker
import com.example.publicplayground.shared_element_transition_and_animateBounds.ShareElementPlayground
import com.example.publicplayground.ui.theme.PublicPlaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        WorkManager.getInstance(this)
            .enqueue(
                OneTimeWorkRequestBuilder<SimpleCoroutineWorker>()
                    .build()
            )

        setContent {
            PublicPlaygroundTheme {
                val view = LocalView.current
                if (!view.isInEditMode) {
                    SideEffect {
                        (view.context as Activity).window.statusBarColor =
                            Color.Transparent.toArgb()
                    }
                }
                PublicPlaygroundApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicPlaygroundApp() {
    ShareElementPlayground(modifier = Modifier)
}

@Preview(showSystemUi = true)
@Composable
fun EdgeToEdgePlaygroundPreview() {
    PublicPlaygroundTheme {
        PublicPlaygroundApp()
    }
}
