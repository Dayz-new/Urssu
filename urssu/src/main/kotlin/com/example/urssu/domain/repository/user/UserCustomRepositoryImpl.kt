package com.example.urssu.domain.repository.user

import com.example.urssu.domain.entity.QUserEntity.userEntity
import com.example.urssu.domain.entity.UserEntity
import com.example.urssu.domain.entity.UserRole
import com.example.urssu.dto.user.ShowReqUserDto
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class UserCustomRepositoryImpl(): UserCustomRepository{

    private lateinit var jpaQueryFactory: JPAQueryFactory

    override fun findALlByShowReqUserDto(showReqUserDto: ShowReqUserDto): List<UserEntity> {
        return jpaQueryFactory.select(userEntity)
            .where(userEntity.role.eq(UserRole.ROLE_USER))
            .where(userEntity.email.eq(showReqUserDto.email))
            .where(userEntity.username.eq(showReqUserDto.username))
            .where(userEntity.created_at.goe(showReqUserDto.createdAtStart))
            .where(userEntity.created_at.loe(showReqUserDto.createdAtEnt))
            .where(userEntity.updated_at.goe(showReqUserDto.updatedAtStart))
            .where(userEntity.updated_at.loe(showReqUserDto.updatedAtEnd))
            .fetch()
    }


}