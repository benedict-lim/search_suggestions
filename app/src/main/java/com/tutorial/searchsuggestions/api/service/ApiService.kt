package com.tutorial.searchsuggestions.api.service

import com.tutorial.searchsuggestions.api.model.SearchSuggestionsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/ac/")
    suspend fun getSearchSuggestions(
        @Query("q") query: String,
        @Query("type") type: String = "list"
    ): SearchSuggestionsResponse
}
