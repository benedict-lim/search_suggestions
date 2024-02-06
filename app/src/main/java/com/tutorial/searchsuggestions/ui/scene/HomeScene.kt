package com.tutorial.searchsuggestions.ui.scene

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
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
        // TODO: Set URL as config
        browserClient.launchUrl("https://www.google.com/search?q=$it".toUri())
    }

    Scaffold(
        content = { padding ->
            HomeSceneContent(
                contentPadding = padding,
                onSearch = { navController.navigate(Router.Search.route) }
            )
        }
    )
}

@Composable
private fun HomeSceneContent(
    contentPadding: PaddingValues,
    onSearch: () -> Unit
) {
    var value by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TitleText()

            SubtitleText()

            SearchTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = value,
                onValueChange = { value = it },
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
