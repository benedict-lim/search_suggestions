package com.tutorial.searchsuggestions

import androidx.navigation.NavHostController

internal enum class Router(val route: String, val param: String) {
    Home("home", ""),
    Search("search", "keyword");

    val fullRoute: String
        get() = if (param.isBlank()) route else "$route?$param={$param}"

    fun createRoute(arg: String): String = if (param.isBlank()) route else "$route?$param=$arg"

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
