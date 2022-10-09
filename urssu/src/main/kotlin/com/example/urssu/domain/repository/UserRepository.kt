package com.example.urssu.domain.repository

import com.example.urssu.domain.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<UserEntity, Int> {
    fun findByEmail(email: String): Optional<UserEntity>
    fun findByEmailAndPassword(email: String, password: String): Optional<UserEntity>

}