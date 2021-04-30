package com.kairosapp.githubkotlinrepositories.api

import com.kairosapp.githubkotlinrepositories.data.IssueApi
import com.kairosapp.githubkotlinrepositories.data.RepositoryResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryRetriever {
    private val service: GithubService

    companion object {
        const val BASE_URL = "https://api.github.com"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(GithubService::class.java)
    }

    suspend fun getRepositories(): RepositoryResult {
        return service.fetchRepositories(
            query = "language:kotlin",
            sort = "stars",
            order = "desc",
            perPage = 100,
            page = 1
        )
    }

    suspend fun getIssues(
        owner: String,
        repo: String,
    ): List<IssueApi> {
        return service.fetchRepoIssues(
            owner = owner,
            repo = repo,
            perPage = 100,
            page = 1
        )
    }
}