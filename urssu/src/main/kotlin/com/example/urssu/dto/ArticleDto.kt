package com.example.urssu.dto

import com.example.urssu.domain.entity.ArticleEntity

data class ArticleDto (
    val articleId: Int,
    val content: String,
    val title: String,
    val userId: Int
){
    fun toEntity(articleDto: ArticleDto): ArticleEntity{
        return ArticleEntity(
            articleDto.articleId,
            articleDto.content,
            articleDto.title,
            articleDto.userId
        )
    }
}
