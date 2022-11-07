package com.example.urssu.dto

import com.example.urssu.domain.entity.UserRole

data class JoinResUserDto (
    val email: String,
    val username: String,
    val role: UserRole
)