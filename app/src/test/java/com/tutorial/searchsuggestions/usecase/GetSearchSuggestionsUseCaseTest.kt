package com.tutorial.searchsuggestions.usecase

import com.tutorial.searchsuggestions.api.model.SearchSuggestionsResponse
import com.tutorial.searchsuggestions.api.service.ApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.stubbing

class GetSearchSuggestionsUseCaseTest {
    private val apiService: ApiService = mock()
    private val useCase = GetSearchSuggestionsUseCaseImpl(apiService)

    @Test
    fun getSearchSuggestions_Success() {
        stubbing(apiService) {
            onBlocking { getSearchSuggestions("android") } doReturn response
        }

        val result = runBlocking { useCase.getSearchSuggestions("android") }
        assertEquals(response, result)
    }

    @Test(expected = RuntimeException::class)
    fun getSearchSuggestions_Error() {
        val error = RuntimeException()
        stubbing(apiService) {
            onBlocking { getSearchSuggestions("android") } doThrow error
        }

        runBlocking { useCase.getSearchSuggestions("android") }
    }

    private companion object {
        val response = SearchSuggestionsResponse(
            keyword = "android",
            results = listOf("Android 14", "Android Architecture Components")
        )
    }
}
