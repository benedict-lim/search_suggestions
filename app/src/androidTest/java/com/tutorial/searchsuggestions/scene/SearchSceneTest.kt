package com.tutorial.searchsuggestions.scene

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextClearance
import androidx.navigation.compose.rememberNavController
import com.tutorial.searchsuggestions.LocalNavController
import com.tutorial.searchsuggestions.api.model.SearchSuggestionsResponse
import com.tutorial.searchsuggestions.ui.scene.SearchScene
import com.tutorial.searchsuggestions.usecase.GetSearchSuggestionsUseCase
import com.tutorial.searchsuggestions.viewmodel.SearchViewModel
import com.tutorial.searchsuggestions.withContentDescription
import com.tutorial.searchsuggestions.withRole
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stubbing

class SearchSceneTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val composeTestRule = createComposeRule()

    private val useCase: GetSearchSuggestionsUseCase = mock()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun search() {
        stubbing(useCase) {
            onBlocking { getSearchSuggestions("android") } doReturn initialResponse
            onBlocking { getSearchSuggestions("Android 14") } doReturn subsequentResponse
        }
        
        val viewModel = createViewModel()

        composeTestRule.setContent { 
            val navController = rememberNavController()
            
            CompositionLocalProvider(LocalNavController provides navController) {
                SearchScene(initialSearchTerm = "android", viewModel = viewModel)
            }
        }

        // Initial search term is set
        composeTestRule.waitUntilExactlyOneExists(hasText("android"))

        // Initial search suggestions are displayed
        composeTestRule.waitUntilExactlyOneExists(hasText("Android 14"))
        composeTestRule.waitUntilExactlyOneExists(hasText("Android Architecture Components"))

        // Tap search icon to update search term
        composeTestRule.selectDropDownItem(value = "Android 14")

        // Subsequent search suggestions are displayed
        composeTestRule.waitUntilExactlyOneExists(hasText("Android 14 New Features"))
        composeTestRule.waitUntilExactlyOneExists(hasText("Android 14 What's Next"))

        // Clear search term and attempt to search
        composeTestRule.onNodeWithTag("searchBar").performTextClearance()
        composeTestRule.onNodeWithTag("searchBar").performImeAction()

        // Validation message is displayed in dialog
        composeTestRule.waitUntilExactlyOneExists(hasText("At least one character is required"))
    }
    
    private fun createViewModel() = SearchViewModel(useCase)

    private companion object {
        val initialResponse = SearchSuggestionsResponse(
            keyword = "android",
            results = listOf("Android 14", "Android Architecture Components")
        )

        val subsequentResponse = SearchSuggestionsResponse(
            keyword = "Android 14",
            results = listOf("Android 14 New Features", "Android 14 What's Next")
        )
    }
}

fun ComposeContentTestRule.selectDropDownItem(value: String) =
    onNode(
        withRole(Role.Image)
            .and(withContentDescription("Select $value"))
    ).performClick()
