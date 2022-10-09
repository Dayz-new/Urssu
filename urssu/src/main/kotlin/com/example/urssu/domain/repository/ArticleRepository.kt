package com.example.urssu.domain.repository

import com.example.urssu.domain.entity.ArticleEntity
import com.example.urssu.domain.entity.CommentEntity
import com.example.urssu.domain.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ArticleRepository : JpaRepository<ArticleEntity, Int> {
    @Query("select a from ArticleEntity a join fetch a.userEntity u where u.email = :email and u.password = :password")
    fun findAllByEmailAndPassword(@Param("email")email: String, @Param("password")password: String): List<ArticleEntity>
}