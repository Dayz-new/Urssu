package com.example.urssu.dto

import com.example.urssu.domain.entity.UserEntity

data class UserInfoDto (
    val email: String,
    val password: String
){
    fun toEntity(): UserEntity {
        return UserEntity(
            email = email,
            password = password
        )
    }
}