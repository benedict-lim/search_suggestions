package com.tutorial.searchsuggestions.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tutorial.searchsuggestions.R
import com.tutorial.searchsuggestions.ui.theme.AppTheme

@Composable
fun SearchSuggestionItem(
    keyword: String,
    value: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchIcon()

        ValueText(keyword = keyword, value = value)

        SelectIcon(value = value, onSelect = onSelect)
    }
}

@Composable
private fun SearchIcon() {
    Icon(
        imageVector = Icons.Filled.Search,
        tint = Color.DarkGray,
        contentDescription = stringResource(R.string.content_description_search)
    )
}

@Composable
private fun RowScope.ValueText(keyword: String, value: String) {
    Text(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 16.dp),
        text = buildAnnotatedString {
            append(value)

            val startIdx = value.lowercase().indexOf(keyword.lowercase())
            if (startIdx < 0) return@buildAnnotatedString

            val endIdx = startIdx + keyword.length
            addStyle(style = SpanStyle(fontWeight = FontWeight.Bold), start = startIdx, end = endIdx)
        },
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Start
    )
}

@Composable
private fun SelectIcon(value: String, onSelect: (String) -> Unit) {
    Icon(
        modifier = Modifier.clickable { onSelect(value) },
        imageVector = Icons.Filled.ArrowOutward,
        tint = Color.DarkGray,
        contentDescription = stringResource(R.string.content_description_select)
    )
}

@Preview
@Composable
private fun PreviewSearchSuggestionItem() {
    AppTheme {
        Box(modifier = Modifier.background(color = Color.White)) {
            SearchSuggestionItem(
                keyword = "disney",
                value = "Things to do at Tokyo Disneyland",
                onSelect = {}
            )
        }
    }
}
