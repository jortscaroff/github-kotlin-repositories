package com.kairosapp.githubkotlinrepositories.data

import com.google.gson.annotations.SerializedName

data class RepositoryResult(val items: List<Item>)

data class Item(
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
    val stargazersCount: Long?,

    @SerializedName("watchers_count")
    val watchersCount: Long?,

    @SerializedName("forks_count")
    val forksCount: Long?)

data class Owner(
    @SerializedName("login")
    val login: String?,

    @SerializedName("id")
    val id: Long?,

    @SerializedName("avatar_url")
    val avatarUrl: String?)