package com.tutorial.searchsuggestions.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tutorial.searchsuggestions.ui.theme.AppTheme

@Composable
fun SearchSuggestionsList(
    keyword: String,
    results: List<String>,
    onSelect: (String) -> Unit
) {
    LazyColumn {
        itemsIndexed(results) { index, item ->
            SearchSuggestionItem(
                keyword = keyword,
                value = item,
                onSelect = onSelect
            )

            // Add a divider after each item
            if (index != results.size - 1) {
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.LightGray
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSearchSuggestionsList() {
    AppTheme {
        Box(modifier = Modifier.background(color = Color.White)) {
            SearchSuggestionsList(
                keyword = "disney",
                results = listOf(
                    "Things to do at Tokyo Disneyland",
                    "Which is the best Disney park in the world?",
                    "Why do people love Disney so much?"
                ),
                onSelect = {}
            )
        }
    }
}
