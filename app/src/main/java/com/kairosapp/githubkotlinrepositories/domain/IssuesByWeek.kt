package com.kairosapp.githubkotlinrepositories.domain

import org.threeten.bp.LocalDateTime

data class IssuesByWeek(
    var amount: Int,
    val week: Int,
    val weekStartDate: LocalDateTime,
    val weekEndDate: LocalDateTime
)
