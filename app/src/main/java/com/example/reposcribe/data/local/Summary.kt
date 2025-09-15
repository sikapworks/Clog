package com.example.reposcribe.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "summary")
data class Summary(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val owner: String,
    val repo: String,
    val summaryText: String,  // AI processed commit summary
    val generatedAt: Long  // store timestamp in milliseconds
)