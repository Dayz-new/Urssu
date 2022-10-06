package com.example.urssu.dto

import com.example.urssu.domain.entity.CommentEntity

data class CommentDto (
    val commentId: Int,
    val content: String,
    val articleId: Int,
    val userId: Int
){
    fun toEntity(commentDto: CommentDto): CommentEntity {
        return CommentEntity(
            commentDto.commentId,
            commentDto.content,
            commentDto.articleId,
            commentDto.userId
        )
    }
}
