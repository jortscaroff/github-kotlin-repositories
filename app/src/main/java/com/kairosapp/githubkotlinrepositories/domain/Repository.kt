package com.kairosapp.githubkotlinrepositories.domain

data class Repository(
    val id: Long?,
    val name: String?,
    val fullName: String?,
    val owner: Owner,
    val private: Boolean,
    val htmlUrl: String?,
    val description: String?,
    val stargazersCount: Int?,
    val watchersCount: Int?,
    val forksCount: Int?,
    val openIssuesCount: Int?
)