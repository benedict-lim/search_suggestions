package com.tutorial.searchsuggestions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tutorial.searchsuggestions.browser.BrowserClient
import com.tutorial.searchsuggestions.ui.scene.HomeScene
import com.tutorial.searchsuggestions.ui.scene.SearchScene
import com.tutorial.searchsuggestions.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var browserClient: BrowserClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
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
                HomeScene(browserClient)
            }
            composable(Router.Search.route) {
                SearchScene()
            }
        }
    }
}
