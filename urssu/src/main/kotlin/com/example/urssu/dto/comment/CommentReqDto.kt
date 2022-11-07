package com.example.urssu.dto.comment

import com.example.urssu.domain.entity.ArticleEntity
import com.example.urssu.domain.entity.CommentEntity
import com.example.urssu.domain.entity.UserEntity
import javax.validation.constraints.NotBlank

data class CommentReqDto (
    val email: String,

    val password: String,

    @field: NotBlank
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
