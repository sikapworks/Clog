package com.example.reposcribe.domain.repository

import com.example.reposcribe.data.local.Summary
import com.example.reposcribe.data.local.SummaryDao
import javax.inject.Inject

class SummaryRepository @Inject constructor(
    private val summaryDao: SummaryDao
) {
    suspend fun getLatestSummary(owner: String, repo: String): Summary? {
        return summaryDao.getLatestSummary(owner, repo)
    }

    suspend fun storeSummary(owner: String, repo: String, summaryText: String, generatedAt: Long) {
        val summary = Summary(
            owner = owner,
            repo = repo,
            summaryText = summaryText,
            generatedAt = generatedAt
        )
        summaryDao.insertSummary(summary)
    }

    suspend fun cleanupOdlSummaries(olderThanTimestamp: Long) {
        summaryDao.deleteOldSummaries(olderThanTimestamp)
    }
}