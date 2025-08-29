package com.example.reposcribe.domain.model

data class Repo(
    val id: Long,
    val name: String,
    val language: String?,
    val htmlUrl: String,
    val updatedIso: String?
)

data class Commit(
    val sha: String,   // 40 char unique code for each commit
    val message: String,
    val authorName: String?,
    val dateIso: String?
)