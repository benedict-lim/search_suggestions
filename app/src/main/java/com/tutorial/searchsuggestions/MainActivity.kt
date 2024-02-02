package com.tutorial.searchsuggestions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tutorial.searchsuggestions.ui.scene.HomeScene
import com.tutorial.searchsuggestions.ui.scene.SearchScene
import com.tutorial.searchsuggestions.ui.theme.SearchSuggestionsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SearchSuggestionsTheme {
                SearchSuggestionsApp()
            }
        }
    }

    @Composable
    private fun SearchSuggestionsApp() {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Router.Home.route
        ) {
            composable(Router.Home.route) {
                HomeScene()
            }
            composable(Router.Search.route) {
                SearchScene()
            }
        }
    }
}
