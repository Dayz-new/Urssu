package com.example.urssu.dto

import com.example.urssu.domain.entity.ArticleEntity
import com.example.urssu.domain.entity.CommentEntity
import com.example.urssu.domain.entity.UserEntity

data class CommentReqDto (
    val email: String,
    val password: String,
    val content: String
){
    fun toEntity(userEntity: UserEntity, articleEntity: ArticleEntity): CommentEntity {
        return CommentEntity(
            content = content,
            articleEntity = articleEntity,
            userEntity = userEntity
        )
    }
}
