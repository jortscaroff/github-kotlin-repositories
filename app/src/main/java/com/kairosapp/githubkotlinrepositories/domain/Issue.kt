package com.kairosapp.githubkotlinrepositories.domain

import org.threeten.bp.LocalDateTime

data class Issue(
    val id: Int,
    val createdAt: LocalDateTime
)