package com.tutorial.searchsuggestions.api.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchSuggestionsResponse(
    val keyword: String,
    val results: List<String>
) : Parcelable
