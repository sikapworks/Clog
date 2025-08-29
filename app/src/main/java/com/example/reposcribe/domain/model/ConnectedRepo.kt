package com.example.reposcribe.domain.model

data class ConnectedRepo (
    val owner: String = "",
    val name: String = "",
    val repoId: String = "${owner}/${name}"
)