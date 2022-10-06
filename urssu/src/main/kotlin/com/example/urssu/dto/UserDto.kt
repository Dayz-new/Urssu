package com.example.urssu.dto

import com.example.urssu.domain.entity.UserEntity

data class UserDto (
    val userId: Int,
    val email: String,
    val password: String,
    val userName: String
){
    fun toEntity(userDto: UserDto): UserEntity {
        return UserEntity(
            userDto.userId,
            userDto.email,
            userDto.password,
            userDto.userName
        )
    }
}