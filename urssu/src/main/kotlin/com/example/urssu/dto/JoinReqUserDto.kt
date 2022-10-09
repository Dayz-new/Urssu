package com.example.urssu.dto

import com.example.urssu.domain.entity.UserEntity

data class JoinReqUserDto (
    val email: String,
    val password: String,
    val username: String
){
    fun toEntity(): UserEntity {
        return UserEntity(
            email = email,
            password = password,
            username = username
        )
    }
}