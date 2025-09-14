package com.example.reposcribe.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SummaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummary(summary: Summary)

    @Query("SELECT * FROM summary WHERE owner = :owner AND repo = :repo ORDER BY generatedAt DESC LIMIT 1")
    suspend fun getLatestSummary(owner: String, repo: String): Summary?

    @Query("DELETE FROM summary WHERE generatedAt < :thresholdTimestamp")
    suspend fun deleteOldSummaries(thresholdTimestamp: Long)
}