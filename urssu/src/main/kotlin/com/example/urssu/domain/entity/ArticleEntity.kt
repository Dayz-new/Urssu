package com.example.urssu.domain.entity

import com.example.urssu.dto.ArticleDto

data class ArticleEntity (
    val articleId: Int,
    val content: String,
    val title: String,
    val userId: Int
){
    fun toDto(articleEntity: ArticleEntity): ArticleDto {
        return ArticleDto(
            articleEntity.articleId,
            articleEntity.content,
            articleEntity.title,
            articleEntity.userId
        )
    }
}