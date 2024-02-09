package com.tutorial.searchsuggestions.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tutorial.searchsuggestions.R
import com.tutorial.searchsuggestions.ui.theme.AppTheme

// Constants
private val ROUNDED_CORNER_SHAPE_DP = 8.dp
private val BACKGROUND_COLOR = Color.White
private val CONTENT_PADDING_DP = 16.dp
private val TITLE_FONT_SIZE = 16.sp
private val TITLE_CONTENT_VERTICAL_SPACING = 16.dp

@Composable
fun ContentDialog(
    title: String? = null,
    properties: DialogProperties = DialogProperties(),
    cancellable: Boolean = true,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest, properties = properties) {
        Surface(
            shape = RoundedCornerShape(ROUNDED_CORNER_SHAPE_DP),
            color = BACKGROUND_COLOR
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(CONTENT_PADDING_DP)
            ) {
                // Close button (display only if cancellable)
                if (cancellable) CloseButton(onDismissRequest = onDismissRequest)

                // Title (display only if defined)
                title?.let { DialogTitle(title = it) }

                // Spacing between title and content
                Spacer(modifier = Modifier.height(TITLE_CONTENT_VERTICAL_SPACING))

                // Container for displaying content
                ContentContainer(content = content)
            }
        }
    }
}

@Composable
private fun ColumnScope.CloseButton(onDismissRequest: () -> Unit) {
    Icon(
        modifier = Modifier
            .align(alignment = Alignment.End)
            .clickable { onDismissRequest() },
        imageVector = Icons.Filled.Close,
        contentDescription = stringResource(R.string.content_description_close)
    )
}

@Composable
private fun ColumnScope.DialogTitle(title: String) {
    Text(
        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
        text = title,
        color = Color.Black,
        fontSize = TITLE_FONT_SIZE,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun ContentContainer(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview
@Composable
private fun PreviewContentDialog() {
    var showDialog by remember { mutableStateOf(false) }

    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Button(label = "Click to display dialog", onClick = { showDialog = true })
        }

        if (showDialog) {
            ContentDialog(title = "Contents", onDismissRequest = { showDialog = false }) {
                Text(
                    text = "Hello World",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
