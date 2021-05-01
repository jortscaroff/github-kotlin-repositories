package com.kairosapp.githubkotlinrepositories.data

import com.google.gson.annotations.SerializedName
import com.kairosapp.githubkotlinrepositories.domain.Owner

data class OwnerApi(
    @SerializedName("login")
    val login: String?,

    @SerializedName("id")
    val id: Long?,

    @SerializedName("avatar_url")
    val avatarUrl: String?
) {
    fun toModel(): Owner {
        return Owner(login, id, avatarUrl)
    }
}