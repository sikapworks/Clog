package com.example.reposcribe.di

import com.example.reposcribe.annotation.AiOkHttp
import com.example.reposcribe.annotation.AiRetrofit
import com.example.reposcribe.data.remote.AiApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AiNetworkModule {

    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    @Provides
    @Singleton
    @AiOkHttp
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    @AiRetrofit
    fun provideRetrofit(@AiOkHttp okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory((GsonConverterFactory.create()))
            .build()
    }

    @Provides
    @Singleton
    fun provideAiApiService(@AiRetrofit retrofit: Retrofit): AiApiService {
        return retrofit.create(AiApiService::class.java)
    }
}