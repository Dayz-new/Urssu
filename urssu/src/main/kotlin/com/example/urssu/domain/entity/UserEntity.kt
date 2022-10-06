package com.example.urssu.domain.entity

import com.example.urssu.dto.UserDto

data class UserEntity (
    val userId: Int,
    val email: String,
    val password: String,
    val userName: String
){
    fun toDto(userEntity: UserEntity): UserDto {
        return UserDto(
            userEntity.userId,
            userEntity.email,
            userEntity.password,
            userEntity.userName
        )
    }
}