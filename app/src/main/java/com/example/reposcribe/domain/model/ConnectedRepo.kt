package com.example.reposcribe.domain.model

data class ConnectedRepo (
    val owner: String = "",
    val name: String = "",
//    val repoId: String = "${owner}/${name}"
    val repoId: String = "" //set explicitly but ?????
) {
    companion object {
        fun from(owner: String, name: String): ConnectedRepo {
            return ConnectedRepo(
                owner = owner,
                name = name,
                repoId = "${owner}/${name}"
            )
        }
    }
}