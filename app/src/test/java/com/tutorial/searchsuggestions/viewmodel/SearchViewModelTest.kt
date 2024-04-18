package com.tutorial.searchsuggestions.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tutorial.searchsuggestions.CoroutineDispatcherRule
import com.tutorial.searchsuggestions.api.model.SearchSuggestionsResponse
import com.tutorial.searchsuggestions.usecase.GetSearchSuggestionsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.stubbing

@ExperimentalCoroutinesApi
class SearchViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineDispatcherRule = CoroutineDispatcherRule()

    private val useCase: GetSearchSuggestionsUseCase = mock()

    @Test
    fun search_Success() {
        stubbing(useCase) {
            onBlocking { getSearchSuggestions("android") } doReturn response
        }

        val testUseCase = object : TestUseCase(useCase) {
            override suspend fun getSearchSuggestions(query: String): SearchSuggestionsResponse {
                return delay(100).run { super.getSearchSuggestions(query) }
            }
        }

        val viewModel = createViewModel(useCase = testUseCase)

        viewModel.onQueryChange("ios")
        coroutineDispatcherRule.testDispatcher.scheduler.apply {
            advanceTimeBy(300L)
            runCurrent()
        }
        assertTrue(viewModel.uiState.value.isLoading)

        // Stop loading and do not display previous results when input empty
        viewModel.onQueryChange("")
        assertFalse(viewModel.uiState.value.isLoading)
        assertNull(viewModel.uiState.value.response)

        viewModel.onQueryChange("android")
        coroutineDispatcherRule.testDispatcher.scheduler.apply {
            advanceTimeBy(300L)
            runCurrent()
        }
        assertTrue(viewModel.uiState.value.isLoading)

        coroutineDispatcherRule.testDispatcher.scheduler.apply {
            advanceTimeBy(100L)
            runCurrent()
        }
        assertFalse(viewModel.uiState.value.isLoading)
        assertEquals(response, viewModel.uiState.value.response)
    }

    @Test
    fun search_Error() {
        val error = RuntimeException()
        stubbing(useCase) {
            onBlocking { getSearchSuggestions(any()) } doThrow error
        }

        val testUseCase = object : TestUseCase(useCase) {
            override suspend fun getSearchSuggestions(query: String): SearchSuggestionsResponse {
                return delay(100).run { super.getSearchSuggestions(query) }
            }
        }

        val viewModel = createViewModel(useCase = testUseCase)

        viewModel.onQueryChange("android")
        coroutineDispatcherRule.testDispatcher.scheduler.apply {
            advanceTimeBy(300L)
            runCurrent()
        }
        assertTrue(viewModel.uiState.value.isLoading)

        coroutineDispatcherRule.testDispatcher.scheduler.apply {
            advanceTimeBy(100L)
            runCurrent()
        }
        assertFalse(viewModel.uiState.value.isLoading)
        assertEquals(error, viewModel.uiState.value.networkError)
    }

    private fun createViewModel(
        useCase: GetSearchSuggestionsUseCase = this.useCase
    ) = SearchViewModel(useCase)

    private open class TestUseCase(
        useCase: GetSearchSuggestionsUseCase
    ) : GetSearchSuggestionsUseCase by useCase

    private companion object {
        val response = SearchSuggestionsResponse(
            keyword = "android",
            results = listOf("Android 14", "Android Architecture Components")
        )
    }
}
