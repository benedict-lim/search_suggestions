package com.tutorial.searchsuggestions.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tutorial.searchsuggestions.R
import com.tutorial.searchsuggestions.ui.theme.AppTheme

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeholder = { PlaceholderText() },
        leadingIcon = { SearchIcon(onClick = onSearch) }
    )
}

@Composable
private fun PlaceholderText() {
    Text(
        text = stringResource(R.string.placeholder_initial_search_term),
        color = Color.LightGray,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun SearchIcon(onClick: () -> Unit) {
    Icon(
        imageVector = Icons.Filled.Search,
        contentDescription = stringResource(R.string.content_description_search),
        modifier = Modifier.clickable { onClick() },
        tint = Color.DarkGray
    )
}

@Preview
@Composable
private fun PreviewSearchTextField() {
    var value by remember { mutableStateOf("") }

    AppTheme {
        Box(
            modifier = Modifier
                .background(color = Color.White)
                .padding(16.dp)
        ) {
            SearchTextField(
                value = value,
                onValueChange = { value = it },
                onSearch = {}
            )
        }
    }
}
