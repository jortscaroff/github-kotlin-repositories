package com.kairosapp.githubkotlinrepositories.api

import com.kairosapp.githubkotlinrepositories.data.*
import retrofit2.http.*

interface GithubService {
    @GET("/search/repositories")
    suspend fun fetchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String? = "stars",
        @Query("order") order: String? = "desc",
        @Query("per_page") perPage: Int,
        @Query("page") page: Int? = 1
    ): RepositoryApiResponse

    @GET("repos/{owner}/{repo}")
    suspend fun fetchRepoDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): RepositorySubscribersApi

    @GET("repos/{owner}/{repo}/issues")
    suspend fun fetchRepoIssues(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("since") since: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): List<IssueApi>


    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String,
    ): AccessToken

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/user")
    suspend fun getUser(@Header("Authorization") auth: String): User

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repositories")
    suspend fun getRepos(@Header("Authorization") auth: String): User
}