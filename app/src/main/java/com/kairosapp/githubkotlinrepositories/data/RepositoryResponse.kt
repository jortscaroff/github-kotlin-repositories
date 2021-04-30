package com.kairosapp.githubkotlinrepositories.data

import com.google.gson.annotations.SerializedName

data class RepositoryResult(
    @SerializedName("items")
    val items: List<RepositoryApi>
    )

data class RepositoryApi(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("full_name")
    val fullName: String?,

    @SerializedName("owner")
    val owner: Owner,

    @SerializedName("private")
    val private: Boolean,

    @SerializedName("html_url")
    val htmlUrl: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("stargazers_count")
    val stargazersCount: Int?,

    @SerializedName("watchers_count")
    val watchersCount: Int?,

    @SerializedName("forks_count")
    val forksCount: Int?,

    @SerializedName("open_issues_count")
    val openIssuesCount: Int?)

data class Owner(
    @SerializedName("login")
    val login: String?,

    @SerializedName("id")
    val id: Long?,

    @SerializedName("avatar_url")
    val avatarUrl: String?)