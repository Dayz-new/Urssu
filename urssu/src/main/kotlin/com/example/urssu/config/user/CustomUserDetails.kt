package com.example.urssu.config.user

import com.example.urssu.domain.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(userEntity: UserEntity) : UserDetails {
    private val userEntity: UserEntity

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