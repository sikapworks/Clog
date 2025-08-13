package com.example.reposcribe.di

import com.example.reposcribe.data.repository.AuthRepositoryImpl
import com.example.reposcribe.domain.repository.AuthRepository
import com.example.reposcribe.domain.usecase.SignUpUseCase
import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


//This binds the repository interface to its Firebase-based implementation, and also provides FirebaseAuth, FireStore, and the SignUpUseCase
// Hilt dependency injection

@Module
object AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseStore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        db: FirebaseFirestore
    ): AuthRepository = AuthRepositoryImpl(auth, db)

    @Provides
    fun provideSignUpUseCase(authRepository: AuthRepository) = SignUpUseCase(authRepository)
}