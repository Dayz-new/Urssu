package com.example.urssu.dto.user

import com.example.urssu.domain.entity.UserRole

data class LoginResUserDto (
    val email: String,
    val username: String,
    val role: UserRole,
    val accessToken: String,
    val refreshToken: String
)