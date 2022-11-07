package com.example.urssu.domain.entity

import com.example.urssu.dto.JoinResUserDto
import com.example.urssu.dto.UserInfoDto
import org.springframework.lang.Nullable
import javax.persistence.*

@Table(name = "user")
@Entity
data class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val userId: Int = 0,

    val email: String,

    val password: String,

    val username: String,

    val role: UserRole

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