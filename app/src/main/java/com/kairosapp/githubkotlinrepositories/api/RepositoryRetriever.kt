package com.kairosapp.githubkotlinrepositories.api

import com.kairosapp.githubkotlinrepositories.domain.Issue
import com.kairosapp.githubkotlinrepositories.domain.Repository
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

    suspend fun getRepositories(): List<Repository> {
        val apiRepositories = service.fetchRepositories(
            query = "language:kotlin",
            sort = "stars",
            order = "desc",
            perPage = 100,
            page = 1
        )
        val repositoriesList: MutableList<Repository> = mutableListOf()
        apiRepositories.items.forEach{ repository ->
            repositoriesList.add(repository.toModel())
        }
        return repositoriesList
    }

    suspend fun getIssues(
        owner: String,
        repo: String,
    ): List<Issue> {
        val apiIssues = service.fetchRepoIssues(
            owner = owner,
            repo = repo,
            perPage = 100,
            page = 1
        )
        val issueList: MutableList<Issue> = mutableListOf()
        for (apiIssue in apiIssues) {
            issueList.add(apiIssue.toModel())
        }
        return issueList
    }
}