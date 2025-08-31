package com.example.reposcribe.di

import com.example.reposcribe.data.remote.AiApiService
import com.example.reposcribe.data.remote.GithubApiService
import com.example.reposcribe.data.repository.AiRepositoryImpl
import com.example.reposcribe.data.repository.GithubRepositoryImpl
import com.example.reposcribe.domain.repository.AiRepository
import com.example.reposcribe.domain.repository.AuthRepository
import com.example.reposcribe.domain.repository.GithubRepository
import com.example.reposcribe.domain.usecase.GetCurrentUserUseCase
import com.example.reposcribe.domain.usecase.GetRepoDetailsUseCase
import com.example.reposcribe.domain.usecase.GetReposUseCase
import com.example.reposcribe.domain.usecase.GetWeeklyCommitsUseCase
import com.example.reposcribe.domain.usecase.UpdateGithubUsernameUseCase
import com.example.reposcribe.presentation.viewmodel.SummaryViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGithubRepository(
        apiService: GithubApiService
    ): GithubRepository {
        return GithubRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideGetReposUseCase(githubRepository: GithubRepository) = GetReposUseCase(githubRepository)

    @Provides
    @Singleton
    fun provideGetWeeklyCommitsUseCase(githubRepository: GithubRepository) = GetWeeklyCommitsUseCase(githubRepository)

    @Provides
    @Singleton
    fun provideGetRepoDetailsUseCase(
        githubRepository: GithubRepository
    ) = GetRepoDetailsUseCase(githubRepository)

    @Provides
    @Singleton
    fun provideUpdateGithubUsernameUseCase(
        authRepository: AuthRepository
    ): UpdateGithubUsernameUseCase {
        return UpdateGithubUsernameUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideAiRepository(
        apiService: AiApiService
    ): AiRepository {
        return AiRepositoryImpl(apiService)
    }

}