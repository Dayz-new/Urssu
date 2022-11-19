package com.example.urssu.domain.entity

import com.example.urssu.dto.user.JoinResUserDto
import com.example.urssu.dto.user.UserInfoDto
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

    var refreshToken: String?,

    @Enumerated(value = EnumType.STRING) // Enum 값의 index가 아니라 값 자체를 저장하기 위한 어노테이션
    val role: UserRole

) : BaseTimeEntity()
{
    fun toJoinResUserDto(): JoinResUserDto {
        return JoinResUserDto(
            email = email,
            username = username,
            role = role,
        )
    }

    fun toUserInfoDto(): UserInfoDto{
        return UserInfoDto(
            id = userId,
            email =  email,
            username = username,
            role = role,
            createdAt = created_at!!,
            updatedAt = updated_at!!
        )
    }

}