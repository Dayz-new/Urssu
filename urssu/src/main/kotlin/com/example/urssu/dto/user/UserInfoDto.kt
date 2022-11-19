package com.example.urssu.dto.user

import com.example.urssu.domain.entity.UserRole
import java.time.LocalDateTime

data class UserInfoDto (
    val id: Int,
    val email: String,
    val username: String,
    val role: UserRole,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)