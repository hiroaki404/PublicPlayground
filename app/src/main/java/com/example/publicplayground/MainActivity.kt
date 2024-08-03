package com.example.publicplayground

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import com.example.publicplayground.feature.compose_basic.RecompositionCheck
import com.example.publicplayground.ui.theme.PublicPlaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            PublicPlaygroundTheme {
                val view = LocalView.current
                if (!view.isInEditMode) {
                    SideEffect {
                        (view.context as Activity).window.statusBarColor =
                            Color.Transparent.toArgb()
                    }
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PublicPlaygroundApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicPlaygroundApp() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Public Playground @hiroaki404")
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        RecompositionCheck(modifier = Modifier.padding(innerPadding))
    }
}

@Preview(showSystemUi = true)
@Composable
fun EdgeToEdgePlaygroundPreview() {
    PublicPlaygroundTheme {
        PublicPlaygroundApp()
    }
}
