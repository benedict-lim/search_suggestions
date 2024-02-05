package com.tutorial.searchsuggestions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutorial.searchsuggestions.api.model.SearchSuggestionsResponse
import com.tutorial.searchsuggestions.usecase.GetSearchSuggestionsUseCase
import com.tutorial.searchsuggestions.viewmodel.uistate.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: GetSearchSuggestionsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var job: Job? = null

    private fun search() {
        if (uiState.value.query.isBlank() || uiState.value.isLoading) return

        job = viewModelScope.launch {
            // Delay to ensure no more input before executing search
            delay(DEBOUNCE_TIME_MS)

            _uiState.update { it.copy(isLoading = true) }

            runCatching { useCase.getSearchSuggestions(uiState.value.query) }
                .onSuccess { onSearchSuccess(it) }
                .onFailure { onSearchFailure(it) }
        }
    }

    private fun onSearchSuccess(response: SearchSuggestionsResponse) {
        _uiState.update { it.copy(response = response, isLoading = false) }
    }

    private fun onSearchFailure(error: Throwable) {
        _uiState.update { it.copy(response = null, isLoading = false, error = error) }
    }

    fun onQueryChange(value: String) {
        _uiState.update { it.copy(query = value) }

        // Cancel job if underway to re-execute search based on new query
        job?.cancel()

        search()
    }

    fun onClear() {
        _uiState.update { it.copy(query = "") }
    }

    fun onErrorConfirmed() {
        _uiState.update { it.copy(error = null) }
    }

    private companion object {
        const val DEBOUNCE_TIME_MS = 300L
    }
}
