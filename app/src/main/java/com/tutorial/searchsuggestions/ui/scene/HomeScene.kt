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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tutorial.searchsuggestions.LocalNavController
import com.tutorial.searchsuggestions.R
import com.tutorial.searchsuggestions.Router
import com.tutorial.searchsuggestions.browser.BrowserClient
import com.tutorial.searchsuggestions.handleSearchTerm
import com.tutorial.searchsuggestions.ui.component.AlertDialog
import com.tutorial.searchsuggestions.ui.component.SearchTextField
import com.tutorial.searchsuggestions.viewmodel.HomeViewModel
import com.tutorial.searchsuggestions.viewmodel.uistate.HomeUiState

@Composable
fun HomeScene(browserClient: BrowserClient, viewModel: HomeViewModel = hiltViewModel()) {
    val navController = LocalNavController.current

    navController.handleSearchTerm {
        viewModel.onSearchTermConfirmed(it)
    }

    val uiState = viewModel.uiState.collectAsState().value

    Scaffold { padding ->
        HomeSceneContent(
            contentPadding = padding,
            uiState = uiState,
            onSearchTermChange = { viewModel.onSearchTermChange(it) },
            onSearch = {
                navController.navigate(Router.Search.createRoute(uiState.initialSearchTerm))
            },
            onBrowserOpen = {
                val searchTerm = uiState.confirmedSearchTerm ?: return@HomeSceneContent
                browserClient.openSearchResults(searchTerm)
                viewModel.onBrowserOpen()
            }
        )
    }
}

@Composable
private fun HomeSceneContent(
    contentPadding: PaddingValues,
    uiState: HomeUiState,
    onSearchTermChange: (String) -> Unit,
    onSearch: () -> Unit,
    onBrowserOpen: () -> Unit
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
                value = uiState.initialSearchTerm,
                onValueChange = onSearchTermChange,
                onSearch = onSearch
            )
        }
    }

    // When search term is confirmed, display dialog to open search results in browser
    uiState.confirmedSearchTerm?.let {
        AlertDialog(
            title = stringResource(R.string.dialog_title_open_browser),
            text = stringResource(R.string.dialog_text_open_browser, it),
            onConfirm = onBrowserOpen,
            onDismissRequest = {}
        )
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
