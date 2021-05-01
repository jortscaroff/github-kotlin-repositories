package com.kairosapp.githubkotlinrepositories.api

import com.kairosapp.githubkotlinrepositories.data.IssueApi
import com.kairosapp.githubkotlinrepositories.data.RepositoryApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET("/search/repositories")
    suspend fun fetchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String? = "stars",
        @Query("order") order: String? = "desc",
        @Query("per_page") perPage: Int,
        @Query("page") page: Int? = 1
    ): RepositoryApiResponse

    @GET("repos/{owner}/{repo}/issues")
    suspend fun fetchRepoIssues(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): List<IssueApi>
}