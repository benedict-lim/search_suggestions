package com.tutorial.searchsuggestions.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tutorial.searchsuggestions.ui.theme.AppTheme

@Composable
fun AlertDialog(
    title: String,
    text: String,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit
) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(label = stringResource(android.R.string.ok), onClick = onConfirm)
        },
        title = {
            Text(
                text = title,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Text(
                text = text,
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    )
}

@Preview
@Composable
private fun PreviewAlertDialog() {
    var showDialog by remember { mutableStateOf(false) }

    AppTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                label = "Click to display dialog",
                onClick = { showDialog = true }
            )
        }

        if (showDialog) {
            AlertDialog(
                title = "Title",
                text = "Text",
                onConfirm = { showDialog = false },
                onDismissRequest = { showDialog = false }
            )
        }
    }
}
