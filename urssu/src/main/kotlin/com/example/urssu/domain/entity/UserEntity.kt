package com.example.urssu.domain.entity

import com.example.urssu.dto.JoinResUserDto
import com.example.urssu.dto.UserInfoDto
import org.jetbrains.annotations.NotNull
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Table(name = "user")
@Entity
data class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val userId: Int = 0,

    val email: String,

    val password: String,

    val username: String
) : BaseTimeEntity()
{
    fun toJoinResUserDto(): JoinResUserDto {
        return JoinResUserDto(
            email = email,
            username = username
        )
    }

    fun toUserInfoDto(): UserInfoDto {
        return UserInfoDto(
            email = email,
            password = password
        )
    }

}