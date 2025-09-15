package com.example.reposcribe.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
//interface -> defines how room interacts with the database
@Dao
interface CommitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommits(commits: List<Commit>)

    //fetch commits for a repo between two dates
    @Query("SELECT * FROM commits WHERE owner = :owner AND repo = :repo AND date BETWEEN :since AND :until")
    suspend fun getCommitsForRange(
        owner: String,
        repo: String,
        since: String,
        until: String
    ): List<Commit>

    // fetch all commits for repo
    @Query("SELECT * FROM commits WHERE owner = :owner AND repo = :repo")
    suspend fun getAllCommits(owner: String, repo: String): List<Commit>

    // clear commits for repo
    @Query("DELETE FROM commits WHERE owner = :owner AND repo = :repo")
    suspend fun deleteCommits(owner: String, repo: String)
}