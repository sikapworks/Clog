package com.example.reposcribe.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.reposcribe.data.remote.dto.CommitListItemDto
import com.example.reposcribe.data.remote.dto.RepositoryDto
import com.example.reposcribe.domain.model.Commit
import com.example.reposcribe.domain.model.Repo
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun RepositoryDto.toDomain(): Repo = Repo(
    id = id.toLong(),
    name = name,
    language = language,
    htmlUrl = htmlUrl,
//    updatedIso = null  // you can extend DTO later to include pushed_at if needed
    updatedIso = pushedAt?.let { isoString ->
//        isoString.substring(0, 10)  // Date format: 16 Aug 2025
        try {
            val parsed = ZonedDateTime.parse(isoString, DateTimeFormatter.ISO_OFFSET_DATE)
            parsed.format(DateTimeFormatter.ofPattern("dd MM yyyy"))//16 Aug 202516 Aug 2025, 10:30 AM
        }
        catch (e: Exception) {
            isoString //fallback to raw string
        }
    }
)

fun CommitListItemDto.toDomain(): Commit = Commit(
    sha = sha,
    message = commit.message,
    authorName = commit.author?.name ?: author?.login,
    dateIso = commit.author?.date
)

fun Commit.toEntity(owner: String, repoName: String): com.example.reposcribe.data.local.Commit {
    return com.example.reposcribe.data.local.Commit(
        sha = this.sha,
        message = this.message,
        author = this.authorName,
        date = this.dateIso ?: "1970-01-01T00:00:00Z",
        owner = owner,
        repo = repoName
    )
}

fun com.example.reposcribe.data.local.Commit.toDomain(): Commit {
    return Commit(
        sha = this.sha,
        message = this.message,
        authorName = this.author,
        dateIso = this.date
    )

}