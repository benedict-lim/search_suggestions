package com.tutorial.searchsuggestions.di.module

import com.tutorial.searchsuggestions.usecase.GetSearchSuggestionsUseCase
import com.tutorial.searchsuggestions.usecase.GetSearchSuggestionsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun bindGetSearchSuggestionsUseCase(
        useCaseImpl: GetSearchSuggestionsUseCaseImpl
    ) : GetSearchSuggestionsUseCase
}
