package com.tutorial.searchsuggestions.viewmodel.uistate

import android.os.Parcelable
import com.tutorial.searchsuggestions.api.model.SearchSuggestionsResponse
import com.tutorial.searchsuggestions.validation.ValidationError
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchUiState(
    val query: String = "",
    val response: SearchSuggestionsResponse? = null,
    val isLoading: Boolean = false,
    val isConfirmed: Boolean = false,
    val networkError: Throwable? = null,
    val validationError: ValidationError? = null
) : Parcelable
