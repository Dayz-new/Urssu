package com.example.urssu.config.user

import com.example.urssu.domain.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(userEntity: UserEntity) : UserDetails {
    //직접 정의한 User 클래스를 필도로 가지게 함.
    private val userEntity: UserEntity

    //생성자를 통해 유지
    init {
        this.userEntity = userEntity
    }

    fun getUserEntity(): UserEntity {
        return userEntity
    }

    override fun getPassword(): String {
        return userEntity.password
    }

    override fun getUsername(): String {
        return userEntity.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return AuthorityUtils.createAuthorityList(userEntity.role.toString())
    }
}