package com.tutorial.searchsuggestions.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tutorial.searchsuggestions.R
import com.tutorial.searchsuggestions.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onClose: () -> Unit,
    onClear: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    var active by remember { mutableStateOf(false) }

    androidx.compose.material3.SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = { active = it },
        leadingIcon = { CloseButton(onClick = onClose) },
        trailingIcon = { ClearButton(onClick = onClear) }
    ) {
        content()
    }
}

@Composable
private fun CloseButton(onClick: () -> Unit) {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = stringResource(R.string.content_description_close),
        modifier = Modifier.clickable { onClick() },
        tint = Color.Blue
    )
}

@Composable
private fun ClearButton(onClick: () -> Unit) {
    Icon(
        imageVector = Icons.Filled.Clear,
        contentDescription = stringResource(R.string.content_description_clear),
        modifier = Modifier.clickable { onClick() },
        tint = Color.DarkGray
    )
}

@Preview
@Composable
private fun PreviewSearchBar() {
    var query by remember { mutableStateOf("") }

    AppTheme {
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = {},
            onClose = {},
            onClear = {}
        ) {
            // TODO: Add content
        }
    }
}
