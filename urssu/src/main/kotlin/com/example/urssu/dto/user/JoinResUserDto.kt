package com.example.urssu.dto.user

import com.example.urssu.domain.entity.UserRole

data class JoinResUserDto (
    val email: String,
    val username: String,
    val role: UserRole
)