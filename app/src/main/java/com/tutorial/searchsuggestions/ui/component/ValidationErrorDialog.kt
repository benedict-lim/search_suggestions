package com.tutorial.searchsuggestions.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.tutorial.searchsuggestions.R
import com.tutorial.searchsuggestions.validation.ValidationError

@Composable
fun ValidationErrorDialog(error: ValidationError, onConfirm: () -> Unit) {
    AlertDialog(
        title = stringResource(R.string.error),
        text = when (error) {
            is ValidationError.EmptyInput -> stringResource(R.string.validation_error_empty_input)
        },
        onConfirm = onConfirm,
        onDismissRequest = onConfirm
    )
}
