package com.kairosapp.githubkotlinrepositories.api

import com.kairosapp.githubkotlinrepositories.domain.IssuesByWeek
import com.kairosapp.githubkotlinrepositories.domain.Repository

interface RepositoryRetriever {
    suspend fun getRepositories(): List<Repository>

    suspend fun getRepoSubscribersCount(
        owner: String,
        repo: String
    ): Int

    suspend fun getIssuesByWeek(
        owner: String,
        repo: String
    ): List<IssuesByWeek>
}