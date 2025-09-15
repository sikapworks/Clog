package com.example.reposcribe.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commits")
data class Commit (
    @PrimaryKey val sha: String,  // unique commit ID
    val message: String,
    val author: String?,
    val date: String,  //ISO date
    val owner: String,
    val repo: String
)