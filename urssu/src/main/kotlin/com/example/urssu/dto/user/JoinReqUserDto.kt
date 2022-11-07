package com.example.urssu.dto.user

import com.example.urssu.domain.entity.UserEntity
import com.example.urssu.domain.entity.UserRole
import javax.validation.constraints.NotBlank

data class JoinReqUserDto (

    @field: NotBlank
    val email: String,

    @field: NotBlank
    val password: String,

    @field: NotBlank
    val username: String,

    val role: UserRole
){
    fun toEntity(): UserEntity {
        return UserEntity(
            email = email,
            password = password,
            username = username,
            role = UserRole.ROLE_USER
        )
    }
}