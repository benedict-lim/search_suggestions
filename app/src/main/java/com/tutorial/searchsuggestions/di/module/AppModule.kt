package com.tutorial.searchsuggestions.di.module

import android.content.Context
import com.tutorial.searchsuggestions.browser.BrowserClient
import com.tutorial.searchsuggestions.browser.BrowserClientImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class AppModule {

    @Provides
    @ActivityScoped
    fun providesBrowserClient(@ActivityContext context: Context): BrowserClient =
        BrowserClientImpl(context = context)
}
