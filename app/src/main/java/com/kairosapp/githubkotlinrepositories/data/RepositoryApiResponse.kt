package com.kairosapp.githubkotlinrepositories.data

import com.google.gson.annotations.SerializedName

data class RepositoryApiResponse(
    @SerializedName("items")
    val items: List<RepositoryApi>
)