package com.example.reposcribe.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.reposcribe.domain.model.Commit
import com.example.reposcribe.domain.repository.GithubRepository
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class GetWeeklyCommitsUseCase @Inject constructor(
    private val githubRepository: GithubRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(
        owner: String,
        name: String,
//        sinceIso: String,
//        untilIso: String
    ): List<Commit> {
        val formatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC)

        val until = Instant.now()
        val since = until.minus(7, ChronoUnit.DAYS)

        val sinceIso = formatter.format(since)
        val untilIso = formatter.format(until)
        return githubRepository.getCommitsForRange(owner, name, sinceIso, untilIso)
    }
}