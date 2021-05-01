package com.kairosapp.githubkotlinrepositories.data

import com.google.gson.annotations.SerializedName
import com.kairosapp.githubkotlinrepositories.domain.Issue
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class IssueApi(
    @SerializedName("id")
    val id: Int,

    @SerializedName("created_at")
    val createdAt: String
) {
    fun toModel(): Issue {
        return Issue(id, LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_DATE_TIME))
    }
}