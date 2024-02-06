package com.tutorial.searchsuggestions.ui.scene

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.tutorial.searchsuggestions.LocalNavController
import com.tutorial.searchsuggestions.R
import com.tutorial.searchsuggestions.Router
import com.tutorial.searchsuggestions.browser.BrowserClient
import com.tutorial.searchsuggestions.handleSearchTerm
import com.tutorial.searchsuggestions.ui.component.Button
import com.tutorial.searchsuggestions.viewmodel.HomeViewModel

@Composable
fun HomeScene(browserClient: BrowserClient, viewModel: HomeViewModel = hiltViewModel()) {
    val navController = LocalNavController.current

    // Retrieve search term and open search results
    navController.handleSearchTerm {
        // TODO: Set URL as config
        browserClient.launchUrl("https://www.google.com/search?q=$it".toUri())
    }

    Scaffold(
        content = { padding ->
            HomeSceneContent(contentPadding = padding) {
                navController.navigate(Router.Search.route)
            }
        }
    )
}

@Composable
private fun HomeSceneContent(
    contentPadding: PaddingValues,
    onButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        Button(label = stringResource(R.string.label_search_button), onClick = onButtonClick)
    }
}
