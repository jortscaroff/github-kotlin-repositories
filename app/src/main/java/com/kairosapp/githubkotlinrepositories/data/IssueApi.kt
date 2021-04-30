package com.kairosapp.githubkotlinrepositories.data

import com.google.gson.annotations.SerializedName

data class IssueApi(
    @SerializedName("id")
    val id: Int,

    @SerializedName("created_at")
    val createdAt: String)