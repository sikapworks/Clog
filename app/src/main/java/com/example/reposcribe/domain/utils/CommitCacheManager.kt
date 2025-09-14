package com.example.reposcribe.domain.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.reposcribe.domain.model.Commit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.Instant

object CommitCacheManager {

    private val _commits = MutableStateFlow<List<Commit>>(emptyList())
    val commits: StateFlow<List<Commit>> = _commits

    fun addCommits(newCommit: List<Commit>) {
        // prevent duplicates by filtering out commits already in the cache
        val existingCommitIds = _commits.value.map { it.sha }.toSet()
        val filteredNewCommits = newCommit.filter { it.sha !in existingCommitIds }

        if (filteredNewCommits.isNotEmpty()) {
            _commits.value = _commits.value + filteredNewCommits
        }
    }

    fun getAllCommits(): List<Commit> = _commits.value

    @RequiresApi(Build.VERSION_CODES.O)
    fun getLatestCommitTimestamp(): String {
        return _commits.value.maxByOrNull { Instant.parse(it.dateIso) }?.dateIso
            ?: "1970-01-01T00:00:00Z"
    }

    fun clearCommits() {
        _commits.value = emptyList()
    }
}