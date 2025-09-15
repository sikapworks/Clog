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
    updatedIso = pushedAt?.let { isoString ->
        try {
            //parse to human readable format
            val parsed = ZonedDateTime.parse(isoString, DateTimeFormatter.ISO_OFFSET_DATE)
            parsed.format(DateTimeFormatter.ofPattern("dd MM yyyy"))//16 Aug 2025
        }
        catch (e: Exception) {
            isoString //fallback to raw string
        }
    }
)

// convert API commit DTO to domain commit
fun CommitListItemDto.toDomain(): Commit = Commit(
    sha = sha,
    message = commit.message,
    authorName = commit.author?.name ?: author?.login,
    dateIso = commit.author?.date
)

// convert domain -> DB entity
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

// convert DB entity -> domain
fun com.example.reposcribe.data.local.Commit.toDomain(): Commit {
    return Commit(
        sha = this.sha,
        message = this.message,
        authorName = this.author,
        dateIso = this.date
    )

}