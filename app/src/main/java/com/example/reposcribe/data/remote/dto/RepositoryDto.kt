package com.example.reposcribe.data.remote.dto

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


data class RepositoryDto (
    val id: Int,
    val name: String,
    @SerializedName("html_url") val htmlUrl: String,   //html_url -> JSON key, htmlUrl -> Kotlin property
    val description: String?,
    val language: String?,
    @SerializedName("pushed_at") val pushedAt: String
)
