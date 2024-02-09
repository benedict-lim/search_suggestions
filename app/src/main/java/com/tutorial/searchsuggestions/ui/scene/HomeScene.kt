package com.tutorial.searchsuggestions.ui.scene

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tutorial.searchsuggestions.LocalNavController
import com.tutorial.searchsuggestions.R
import com.tutorial.searchsuggestions.Router
import com.tutorial.searchsuggestions.browser.BrowserClient
import com.tutorial.searchsuggestions.handleSearchTerm
import com.tutorial.searchsuggestions.ui.component.SearchTextField

@Composable
fun HomeScene(browserClient: BrowserClient) {
    val navController = LocalNavController.current

    // Retrieve search term and open search results
    navController.handleSearchTerm {
        browserClient.openSearchResults(it)
    }

    // Retain search term when returning from search suggestions screen
    var searchTerm by rememberSaveable { mutableStateOf("") }

    Scaffold { padding ->
        HomeSceneContent(
            contentPadding = padding,
            searchTerm = searchTerm,
            onSearchTermChange = { searchTerm = it },
            onSearch = { navController.navigate(Router.Search.createRoute(searchTerm)) }
        )
    }
}

@Composable
private fun HomeSceneContent(
    contentPadding: PaddingValues,
    searchTerm: String,
    onSearchTermChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TitleText()

            SubtitleText()

            SearchTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = searchTerm,
                onValueChange = onSearchTermChange,
                onSearch = onSearch
            )
        }
    }
}

@Composable
private fun TitleText() {
    Text(
        text = stringResource(R.string.app_name),
        color = Color.Black,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun SubtitleText() {
    Text(
        text = stringResource(R.string.subtitle_home),
        color = Color.LightGray,
        style = MaterialTheme.typography.titleMedium
    )
}
