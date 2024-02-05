package com.tutorial.searchsuggestions

import androidx.navigation.NavHostController

internal enum class Router(val route: String) {
    Home("home"),
    Search("search");

    companion object {
        const val ARG_QUERY = "query"
    }
}

fun NavHostController.setSearchTerm(query: String) {
    previousBackStackEntry?.savedStateHandle?.set(Router.ARG_QUERY, query)
    popBackStack()
}

fun NavHostController.handleSearchTerm(handler: (String) -> Unit) {
    currentBackStackEntry?.savedStateHandle?.get<String>(Router.ARG_QUERY)?.let {
        handler(it)
        currentBackStackEntry?.savedStateHandle?.remove<String>(Router.ARG_QUERY)
    }
}
