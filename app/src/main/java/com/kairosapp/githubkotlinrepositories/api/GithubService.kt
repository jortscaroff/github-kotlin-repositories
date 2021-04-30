package com.kairosapp.githubkotlinrepositories.api

import com.kairosapp.githubkotlinrepositories.data.RepositoryResult
import retrofit2.http.GET

interface GithubService {
    @GET("/search/repositories?q=language:kotlin&sort=stars&order=desc&per_page=100")
    suspend fun searchRepositories(): RepositoryResult

}