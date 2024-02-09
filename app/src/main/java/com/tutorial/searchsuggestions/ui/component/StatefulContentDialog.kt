package com.tutorial.searchsuggestions.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.tutorial.searchsuggestions.ui.theme.AppTheme

@Composable
fun StatefulContentDialog(
    state: ContentDialogState,
    title: String? = null,
    properties: DialogProperties = DialogProperties(),
    cancellable: Boolean = true,
    onDismissRequest: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    if (state.showDialog) {
        ContentDialog(
            title = title,
            properties = properties,
            cancellable = cancellable,
            onDismissRequest = {
                onDismissRequest()
                state.hide()
            },
            content = content
        )
    }
}

@Composable
fun rememberContentDialogState(showDialog: Boolean = false) =
    remember { ContentDialogState(showDialog) }

/** State to control display status of content dialog */
class ContentDialogState(showDialog: Boolean) {
    private var _showDialog = mutableStateOf(showDialog)
    val showDialog: Boolean
        get() = _showDialog.value

    fun show() {
        _showDialog.value = true
    }

    fun hide() {
        _showDialog.value = false
    }
}

@Preview
@Composable
private fun Preview_StatefulContentDialog() {
    val state = rememberContentDialogState()

    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            contentAlignment = Alignment.Center
        ) {
            Button(label = "Click to display dialog", onClick = { state.show() })
        }

        StatefulContentDialog(state = state, title = "Contents") {
            Text(
                text = "Hello World",
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
