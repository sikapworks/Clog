package com.example.reposcribe.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Commit::class, Summary::class],  // DB holds commits + summaries
    version = 1,
    exportSchema = false // avoid exporting schema in prod
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun commitDao(): CommitDao  //DAO for commit operations
    abstract fun summaryDao(): SummaryDao  // DAO for summaries
}