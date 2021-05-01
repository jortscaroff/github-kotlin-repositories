package com.kairosapp.githubkotlinrepositories.domain

import java.time.LocalDateTime

data class Issue(
    val id: Int,
    val createdAt: LocalDateTime
)