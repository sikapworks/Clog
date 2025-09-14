package com.example.reposcribe.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Commit::class, Summary::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun commitDao(): CommitDao
    abstract fun summaryDao(): SummaryDao
}