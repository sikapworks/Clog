package com.example.reposcribe.di

import com.example.reposcribe.data.remote.FirebaseAuthDataSource
import com.example.reposcribe.data.repository.AuthRepositoryImpl
import com.example.reposcribe.domain.repository.AuthRepository
import com.example.reposcribe.domain.usecase.GetCurrentUserUseCase
import com.example.reposcribe.domain.usecase.LoginUseCase
import com.example.reposcribe.domain.usecase.LogoutUseCase
import com.example.reposcribe.domain.usecase.SignUpUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//This binds the repository interface to its Firebase-based implementation, and also provides FirebaseAuth, FireStore, and the SignUpUseCase
// Hilt dependency injection

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAuthDataSource(
        auth: FirebaseAuth,
        db: FirebaseFirestore
    ) = FirebaseAuthDataSource(auth, db)

    @Provides
    @Singleton
    fun provideAuthRepository(
        ds: FirebaseAuthDataSource
    ): AuthRepository = AuthRepositoryImpl(ds)

    @Provides
    @Singleton
    fun provideSignUpUseCase(authRepository: AuthRepository) = SignUpUseCase(authRepository)

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository) = LoginUseCase(authRepository)

    @Provides
    @Singleton
    fun provideGetCurrentUserUseCase(authRepository: AuthRepository) =
        GetCurrentUserUseCase(authRepository)

    @Provides
    @Singleton
    fun provideLogoutUseCase(authRepository: AuthRepository) = LogoutUseCase(authRepository)
}