package com.example.reposcribe.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commits")
data class Commit (
    @PrimaryKey val sha: String,
    val message: String,
    val author: String?,
    val date: String,
    val owner: String,
    val repo: String
)