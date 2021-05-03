package com.kairosapp.githubkotlinrepositories.data

import com.google.gson.annotations.SerializedName

data class RepositorySubscribersApi (
    @SerializedName ("subscribers_count")
    val subscribersCount: Int
)