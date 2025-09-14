package com.example.reposcribe.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommits(commits: List<Commit>)

    @Query("SELECT * FROM commits WHERE owner = :owner AND repo = :repo AND date BETWEEN :since AND :until")
    suspend fun getCommitsForRange(
        owner: String,
        repo: String,
        since: String,
        until: String
    ): List<Commit>

    @Query("SELECT * FROM commits WHERE owner = :owner AND repo = :repo")
    suspend fun getAllCommits(owner: String, repo: String): List<Commit>

    @Query("SELECT * FROM commits WHERE owner = :owner AND repo = :repo")
    suspend fun getCommits(owner: String, repo: String): List<Commit>

    @Query("DELETE FROM commits WHERE owner = :owner AND repo = :repo")
    suspend fun deleteCommits(owner: String, repo: String)
}