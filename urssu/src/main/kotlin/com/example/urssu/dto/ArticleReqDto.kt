package com.example.urssu.dto

import com.example.urssu.domain.entity.ArticleEntity
import com.example.urssu.domain.entity.UserEntity

data class ArticleReqDto (
    val email: String,
    val password: String,
    val title: String,
    val content: String
){
    fun toEntity(userEntity: UserEntity): ArticleEntity{
        return ArticleEntity(
            title = title,
            content = content,
            userEntity = userEntity
        )
    }
}
