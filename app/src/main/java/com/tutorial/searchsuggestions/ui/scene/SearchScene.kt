package com.tutorial.searchsuggestions.ui.scene

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tutorial.searchsuggestions.LocalNavController
import com.tutorial.searchsuggestions.setSearchTerm
import com.tutorial.searchsuggestions.ui.component.SearchBar
import com.tutorial.searchsuggestions.ui.component.SearchSuggestionsList
import com.tutorial.searchsuggestions.ui.component.ValidationErrorDialog
import com.tutorial.searchsuggestions.viewmodel.SearchViewModel
import com.tutorial.searchsuggestions.viewmodel.uistate.SearchUiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun SearchScene(initialSearchTerm: String?, viewModel: SearchViewModel = hiltViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        // Set initial search term in search bar and retrieve results
        if (!initialSearchTerm.isNullOrBlank()) {
            viewModel.onQueryChange(initialSearchTerm)
        }

        // Begin search when query is confirmed
        viewModel.uiState.collectLatest {
            if (it.isConfirmed) navController.setSearchTerm(it.query)
        }
    }

    val uiState = viewModel.uiState.collectAsState().value

    // Display network error in Snackbar
    uiState.networkError?.let {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(it.message.orEmpty())
            viewModel.onNetworkErrorHandled()
        }
    }

    // Display validation error in Dialog
    uiState.validationError?.let {
        ValidationErrorDialog(error = it, onConfirm = { viewModel.onValidationErrorHandled() })
    }

    Scaffold(
        topBar = {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                query = uiState.query,
                onQueryChange = { viewModel.onQueryChange(it) },
                onSearch = { viewModel.onQueryConfirmed() },
                onClose = { navController.popBackStack() },
                onClear = { viewModel.onClear() }
            )
        },
        content = { padding ->
            SearchSceneContent(
                contentPadding = padding,
                uiState = uiState,
                onSelect = { viewModel.onQueryChange(it) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    )
}

@Composable
private fun SearchSceneContent(
    contentPadding: PaddingValues,
    uiState: SearchUiState,
    onSelect: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } },
    ) {
        // Show linear progress indicator when loading, otherwise a divider in place
        if (uiState.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        } else {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.DarkGray
            )
        }

        uiState.response?.let {
            SearchSuggestionsList(
                keyword = it.keyword,
                results = it.results,
                onSelect = onSelect
            )
        }
    }
}
