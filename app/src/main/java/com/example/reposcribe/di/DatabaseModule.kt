package com.example.reposcribe.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.reposcribe.App
import com.example.reposcribe.data.local.AppDatabase
import com.example.reposcribe.data.local.CommitDao
import com.example.reposcribe.data.local.SummaryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideCommitDao(db: AppDatabase): CommitDao {
        return db.commitDao()
    }
    @Provides
    fun provideSummaryDao(db: AppDatabase): SummaryDao {
        return db.summaryDao()
    }
}