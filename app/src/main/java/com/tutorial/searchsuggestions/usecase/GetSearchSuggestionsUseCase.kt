package com.tutorial.searchsuggestions.usecase

import com.tutorial.searchsuggestions.api.service.ApiService
import com.tutorial.searchsuggestions.api.model.SearchSuggestionsResponse
import javax.inject.Inject

interface GetSearchSuggestionsUseCase {
    /**
     * Get list of search suggestions
     *
     * @param query User input
     * @return Payload including keyword & retrieved list of search suggestions
     */
    suspend fun getSearchSuggestions(query: String): SearchSuggestionsResponse
}

class GetSearchSuggestionsUseCaseImpl @Inject constructor(
    private val apiService: ApiService
) : GetSearchSuggestionsUseCase {

    override suspend fun getSearchSuggestions(query: String): SearchSuggestionsResponse =
        apiService.getSearchSuggestions(query)
}
