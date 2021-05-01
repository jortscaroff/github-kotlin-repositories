package com.kairosapp.githubkotlinrepositories.data

import com.google.gson.annotations.SerializedName
import com.kairosapp.githubkotlinrepositories.domain.Issue
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

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