package com.example.urssu.service

import com.example.urssu.config.user.CustomUserDetails
import com.example.urssu.domain.entity.UserEntity
import com.example.urssu.domain.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CustomUserDetailsService : UserDetailsService {
    @Autowired lateinit var userRepository: UserRepository

    // 로그인으로부터 들어온 username으로 DB에서 유저 정보르 찾는 메서드
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val userEntity: UserEntity = userRepository.findByEmail(email)
            .orElseThrow { UsernameNotFoundException("해당 사용자가 존재하지 않습니다. : $email") }

        return CustomUserDetails(userEntity)
    }
}