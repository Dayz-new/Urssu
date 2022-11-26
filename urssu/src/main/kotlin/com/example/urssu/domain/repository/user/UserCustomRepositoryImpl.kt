package com.example.urssu.domain.repository.user

import com.example.urssu.domain.entity.QUserEntity.userEntity
import com.example.urssu.domain.entity.UserEntity
import com.example.urssu.domain.entity.UserRole
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class UserCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
): UserCustomRepository{
    override fun findALlByTime(createdAtStart: LocalDateTime?, createdAtEnd: LocalDateTime?, updatedAtStart: LocalDateTime?, updatedAtEnd: LocalDateTime?): MutableList<UserEntity> {
        if(createdAtStart.toString() != "0001-01-01T00:00:00"
            && createdAtEnd.toString() == "9999-12-31T23:59:59"){
            return jpaQueryFactory.selectFrom(userEntity)
                .where(userEntity.role.eq(UserRole.ROLE_USER))
                .where(userEntity.created_at.goe(createdAtStart))
                .orderBy(userEntity.userId.desc())
                .fetch()
        }else if (createdAtStart.toString() == "0001-01-01T00:00:00"
            && createdAtEnd.toString() != "9999-12-31T23:59:59"){
            return jpaQueryFactory.selectFrom(userEntity)
                .where(userEntity.role.eq(UserRole.ROLE_USER))
                .where(userEntity.created_at.loe(createdAtEnd))
                .orderBy(userEntity.userId.desc())
                .fetch()
        }else{
            return jpaQueryFactory.selectFrom(userEntity)
                .where(userEntity.role.eq(UserRole.ROLE_USER))
                .orderBy(userEntity.userId.desc())
                .fetch()
        }
    }
}