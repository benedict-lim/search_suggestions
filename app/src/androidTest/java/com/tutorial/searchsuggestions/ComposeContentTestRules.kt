package com.tutorial.searchsuggestions

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher

fun withRole(role: Role): SemanticsMatcher =
    SemanticsMatcher.expectValue(SemanticsProperties.Role, role)

fun withContentDescription(contentDescription: String): SemanticsMatcher =
    SemanticsMatcher.expectValue(SemanticsProperties.ContentDescription, listOf(contentDescription))

fun withTestTag(testTag: String): SemanticsMatcher =
    SemanticsMatcher.expectValue(SemanticsProperties.TestTag, testTag)

fun isEditableText(): SemanticsMatcher =
    SemanticsMatcher.keyIsDefined(SemanticsProperties.EditableText)
