package com.example.reposcribe.di

import com.example.reposcribe.data.remote.FireStoreDataSource
import com.example.reposcribe.data.remote.GithubApiService
import com.example.reposcribe.data.repository.ConnectedRepoRepositoryImpl
import com.example.reposcribe.data.repository.GithubRepositoryImpl
import com.example.reposcribe.domain.repository.ConnectedRepoRepository
import com.example.reposcribe.domain.repository.GithubRepository
import com.example.reposcribe.domain.usecase.AddConnectedRepoUseCase
import com.example.reposcribe.domain.usecase.GetConnectedReposUseCase
import com.example.reposcribe.domain.usecase.GetReposUseCase
import com.example.reposcribe.domain.usecase.GetWeeklyCommitsUseCase
import com.example.reposcribe.domain.usecase.IsRepoConnectedUseCase
import com.example.reposcribe.domain.usecase.RemoveConnectedRepoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module    //a container of dependency providers
@InstallIn(SingletonComponent::class)   //only one instance
object NetworkModule {

    private const val BASE_URL = "https://api.github.com/"

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubApiService(retrofit: Retrofit): GithubApiService {
        return retrofit.create(GithubApiService::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideGithubRepository(
//        apiService: GithubApiService
//    ): GithubRepository {
//        return GithubRepositoryImpl(apiService)
//    }

    @Provides
    @Singleton
    fun provideConnectedRepoRepository(
        firestore: FireStoreDataSource
    ): ConnectedRepoRepository {
        return ConnectedRepoRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideGetReposUseCase(githubRepository: GithubRepository) = GetReposUseCase(githubRepository)

//    @Provides
//    @Singleton
//    fun provideGetWeeklyCommitsUseCase(githubRepository: GithubRepository) =
//        GetWeeklyCommitsUseCase(githubRepository)

    @Provides
    @Singleton
    fun provideGetConnectedReposUseCase(repo: ConnectedRepoRepository) = GetConnectedReposUseCase(repo)

    @Provides
    @Singleton
    fun provideAddConnectedRepoUseCase(repo: ConnectedRepoRepository) = AddConnectedRepoUseCase(repo)

    @Provides
    @Singleton
    fun provideRemoveConnectedRepoUseCase(repo: ConnectedRepoRepository) = RemoveConnectedRepoUseCase(repo)

    @Provides
    @Singleton
    fun provideIsRepoConnectedUseCase(repo: ConnectedRepoRepository) = IsRepoConnectedUseCase(repo)
}