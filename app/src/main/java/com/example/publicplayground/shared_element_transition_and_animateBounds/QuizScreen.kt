package com.example.publicplayground.shared_element_transition_and_animateBounds

import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.animateBounds
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.publicplayground.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun QuizScreen(expanded: Boolean, onBack: () -> Unit = {}, onButtonClick: () -> Unit = {}) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("クイズ") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "戻る"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LookaheadScope {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    modifier = Modifier
                        .offset(
                            x = if (expanded) 0.dp else 55.dp,
                            y = if (expanded) 0.dp else 50.dp
                        )
                        .size(if (expanded) 200.dp else 30.dp)
                        .animateBounds(
                            this@LookaheadScope,
                            boundsTransform = BoundsTransform { _, _ ->
                                tween(2000)
                            }),
                    painter = painterResource(id = R.drawable.shimaenaga),
                    contentDescription = null
                )

                Button(
                    onClick = onButtonClick,
                ) {
                    Text(
                        text = stringResource(
                            id = if (expanded) R.string.quiz_button_reset
                            else R.string.quiz_button_initial
                        )
                    )
                }

                if (expanded) {
                    Text(text = stringResource(id = R.string.quiz_answer))
                    Text("AI generated images for the images.")
                }
            }
        }
    }
}

private class ExpandedPreviewParameterProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(true, false)
}

@Preview(showBackground = true)
@Composable
private fun QuizScreenPreview(
    @PreviewParameter(ExpandedPreviewParameterProvider::class) expanded: Boolean
) {
    QuizScreen(expanded = expanded)
} 
