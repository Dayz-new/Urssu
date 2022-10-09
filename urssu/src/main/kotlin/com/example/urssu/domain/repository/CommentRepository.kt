package com.example.urssu.domain.repository

import com.example.urssu.domain.entity.ArticleEntity
import com.example.urssu.domain.entity.CommentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CommentRepository : JpaRepository<CommentEntity, Int> {

    @Query("select c from CommentEntity c join fetch c.userEntity u where u.email = :email and u.password = :password")
    fun findAllByEmailAndPassword(@Param("email")email: String, @Param("password")password: String): List<CommentEntity>

    @Query("select c from CommentEntity c join fetch c.articleEntity a where a.articleId = :articleId")
    fun findAllByArticleId(@Param("articleId")articleId: Int): List<CommentEntity>

    fun delete(commentEntity: CommentEntity?)
}