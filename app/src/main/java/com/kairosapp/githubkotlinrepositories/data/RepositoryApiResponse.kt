package com.kairosapp.githubkotlinrepositories.data

import com.google.gson.annotations.SerializedName
import com.kairosapp.githubkotlinrepositories.domain.Repository

data class RepositoryApiResponse(
    @SerializedName("items")
    val items: List<RepositoryApi>
) {
    fun toModel() : List<Repository> {
        return items.map {
            repositoryApi -> repositoryApi.toModel()
        }
    }
}