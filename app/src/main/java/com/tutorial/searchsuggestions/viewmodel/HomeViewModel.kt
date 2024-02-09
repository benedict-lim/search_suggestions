package com.tutorial.searchsuggestions.viewmodel

import androidx.lifecycle.ViewModel
import com.tutorial.searchsuggestions.viewmodel.uistate.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onSearchTermChange(value: String) {
        _uiState.update { it.copy(initialSearchTerm = value) }
    }

    fun onSearchTermConfirmed(value: String) {
        _uiState.update { it.copy(confirmedSearchTerm = value) }
    }

    fun onBrowserOpen() {
        _uiState.update { it.copy(confirmedSearchTerm = null) }
    }
}
