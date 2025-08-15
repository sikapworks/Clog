package com.example.reposcribe.domain.model

data class User(
    val uid: String,
    val email: String,
    val githubUsername: String?
)