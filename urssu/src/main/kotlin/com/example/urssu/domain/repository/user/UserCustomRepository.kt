package com.example.urssu.domain.repository.user

import com.example.urssu.domain.entity.UserEntity
import com.example.urssu.dto.user.ShowReqUserDto
import org.springframework.data.repository.query.Param

interface UserCustomRepository {
    fun findALlByShowReqUserDto(@Param("ShowReqUserDto") showReqUserDto: ShowReqUserDto): List<UserEntity>
}