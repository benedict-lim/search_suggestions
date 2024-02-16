package com.tutorial.searchsuggestions.validation

fun String.validateNotEmpty(): ValidationError? =
    if (isBlank()) ValidationError.EmptyInput else null
