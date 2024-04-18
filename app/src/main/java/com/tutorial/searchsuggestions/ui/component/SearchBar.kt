package com.tutorial.searchsuggestions.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tutorial.searchsuggestions.R
import com.tutorial.searchsuggestions.ui.theme.AppTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: KeyboardActionScope.() -> Unit,
    onClose: () -> Unit,
    onClear: () -> Unit
) {
    OutlinedTextField(
        modifier = modifier.testTag("searchBar"),
        value = query,
        onValueChange = { onQueryChange(it) },
        placeholder = { PlaceholderText() },
        leadingIcon = { CloseButton(onClick = onClose) },
        trailingIcon = { ClearButton(onClick = onClear) },
        keyboardActions = KeyboardActions(onSearch = onSearch),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
    )
}

@Composable
private fun PlaceholderText() {
    Text(
        text = stringResource(R.string.placeholder_search_bar),
        color = Color.LightGray,
        style = MaterialTheme.typography.bodyMedium
    )
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
        Box(
            modifier = Modifier
                .background(color = Color.White)
                .padding(16.dp)
        ) {
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = {},
                onClose = {},
                onClear = { query = "" }
            )
        }
    }
}
