package com.example.reposcribe.domain.utils

import com.example.reposcribe.data.local.Commit

class CommitCacheManagerTest {
    val commit1 = com.example.reposcribe.domain.model.Commit(
        sha = "abc123def456",
        message = "Initial commit with project structure",
        authorName = "Jane Doe",
        dateIso = "2025-09-01T10:00:00Z"
    )

    val commit2 = com.example.reposcribe.domain.model.Commit(
        sha = "789ghi012jkl",
        message = "Fixed bug in authentication flow",
        authorName = "John Smith",
        dateIso = "2025-09-02T15:30:00Z"
    )

//    @Before
//    fun setup() {
//        CommitCacheManager.clearCommits()
//    }
}