package com.tutorial.searchsuggestions.viewmodel.uistate

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeUiState(
    val initialSearchTerm: String = "",
    val confirmedSearchTerm: String? = null
) : Parcelable
