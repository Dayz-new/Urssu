package com.example.urssu.domain.repository.user

import com.example.urssu.domain.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<UserEntity, Int>, UserCustomRepository {
    fun findByEmail(email: String): Optional<UserEntity>
    // fun findByEmailAndPassword(email: String, password: String): Optional<UserEntity>

    // TODO: 회원가입할 떄 password 인코딩되므로 고려해서 메서드 수정 필요

}