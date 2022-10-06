package com.example.urssu.domain.entity

import com.example.urssu.dto.CommentDto

data class CommentEntity (
    val commentId: Int,
    val content: String,
    val articleId: Int,
    val userId: Int
){
    fun toDto(commentEntity: CommentEntity): CommentDto {
        return CommentDto(
            commentEntity.commentId,
            commentEntity.content,
            commentEntity.articleId,
            commentEntity.userId
        )
    }
}