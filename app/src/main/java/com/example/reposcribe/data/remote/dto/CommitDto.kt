package com.example.reposcribe.data.remote.dto

data class CommitListItemDto(
    val sha: String,
    val commit: CommitDto,
    val author: UserDto?
)

data class CommitDto (
    val message: String,
    val author: CommitAuthorDto?
)

data class CommitAuthorDto(
    val name: String?,
    val email: String?,
    val date: String?
)

data class UserDto(
    val login: String?
)