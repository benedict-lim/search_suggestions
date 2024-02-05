package com.tutorial.searchsuggestions.viewmodel.uistate

import android.os.Parcelable
import com.tutorial.searchsuggestions.api.model.SearchSuggestionsResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchUiState(
    val query: String = "",
    val response: SearchSuggestionsResponse? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null
) : Parcelable
