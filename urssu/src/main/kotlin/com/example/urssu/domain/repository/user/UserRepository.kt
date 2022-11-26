package com.example.urssu.domain.repository.user

import com.example.urssu.domain.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<UserEntity, Int>, UserCustomRepository {
    fun findByEmail(email: String): Optional<UserEntity>

}