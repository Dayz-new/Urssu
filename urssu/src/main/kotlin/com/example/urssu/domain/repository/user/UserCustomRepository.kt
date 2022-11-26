package com.example.urssu.domain.repository.user

import com.example.urssu.domain.entity.UserEntity
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface UserCustomRepository {
    fun findALlByTime(createdAtStart: LocalDateTime?, createdAtEnd: LocalDateTime?, updatedAtStart: LocalDateTime?, updatedAtEnd: LocalDateTime?): MutableList<UserEntity>
}