package com.kairosapp.githubkotlinrepositories.api

import com.kairosapp.githubkotlinrepositories.domain.IssuesByWeek
import com.kairosapp.githubkotlinrepositories.domain.Repository
import org.threeten.bp.LocalDateTime

interface RepositoryRetriever {
    suspend fun getRepositories(): List<Repository>

    suspend fun getIssuesByWeek(
        owner: String,
        repo: String,
        since: LocalDateTime
    ): List<IssuesByWeek>
}