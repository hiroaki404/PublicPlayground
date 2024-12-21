package com.example.publicplayground.keyboard

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun KeyboardPlayground(modifier: Modifier = Modifier) {
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = modifier
            .imePadding()
            .verticalScroll(rememberScrollState())
            .focusRequester(focusRequester)
            .focusable()
            .clickable(interactionSource = interactionSource, indication = null) {
                focusRequester.requestFocus()
            }
            .padding(horizontal = 16.dp)
    ) {
        AllKeyboardTypeTextField()
        AllImeActionTextField()
    }
}

@Composable
fun AllKeyboardTypeTextField() {
    Text(text = "KeyboardType", style = MaterialTheme.typography.titleMedium)
    HorizontalDivider()

    listOf(
        KeyboardType.Unspecified,
        KeyboardType.Text,
        KeyboardType.Ascii,
        KeyboardType.Number,
        KeyboardType.Phone,
        KeyboardType.Uri,
        KeyboardType.Email,
        KeyboardType.Password,
        KeyboardType.NumberPassword,
        KeyboardType.Decimal,
    ).forEach {
        Text("$it")
        var text by remember { mutableStateOf("") }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            singleLine = true,
            onValueChange = { text = it },
            keyboardOptions = KeyboardOptions(keyboardType = it),
        )
    }
}

@Composable
fun AllImeActionTextField() {
    Text(text = "KeyboardAction", style = MaterialTheme.typography.titleMedium)
    HorizontalDivider()

    listOf(
        ImeAction.Unspecified,
        ImeAction.Default,
        ImeAction.None,
        ImeAction.Go,
        ImeAction.Search,
        ImeAction.Send,
        ImeAction.Previous,
        ImeAction.Next,
        ImeAction.Done,
    ).forEach {
        Text("$it")
        var text by remember { mutableStateOf("") }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            singleLine = true,
            onValueChange = { text = it },
            keyboardOptions = KeyboardOptions(imeAction = it),
        )
    }
}


@Preview(showBackground = true, heightDp = 2000)
@Composable
fun KeyboardPlaygroundPreview() {
    KeyboardPlayground(modifier = Modifier.padding(16.dp).border(1.dp, Color.Gray))
}
