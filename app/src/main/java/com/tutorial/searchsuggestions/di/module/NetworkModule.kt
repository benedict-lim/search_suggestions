package com.tutorial.searchsuggestions.di.module

import com.google.gson.GsonBuilder
import com.tutorial.searchsuggestions.BuildConfig
import com.tutorial.searchsuggestions.api.model.SearchSuggestionsResponse
import com.tutorial.searchsuggestions.api.model.deserializer.SearchSuggestionsResponseDeserializer
import com.tutorial.searchsuggestions.api.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.addInterceptor(httpLoggingInterceptor)

        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)

        return builder.build()
    }

    @Provides
    @Singleton
    fun providesApiService(okHttpClient: OkHttpClient): ApiService =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.API_ENDPOINT)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .registerTypeAdapter(
                            SearchSuggestionsResponse::class.java,
                            SearchSuggestionsResponseDeserializer()
                        )
                        .create()
                )
            )
            .build()
            .create(ApiService::class.java)
}
