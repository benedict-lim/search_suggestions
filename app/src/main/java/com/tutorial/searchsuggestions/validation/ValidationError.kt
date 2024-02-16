package com.tutorial.searchsuggestions.validation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class ValidationError : Parcelable {

    @Parcelize
    data object EmptyInput : ValidationError()
}
