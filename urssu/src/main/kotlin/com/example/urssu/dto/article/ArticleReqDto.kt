package com.example.urssu.dto.article

import com.example.urssu.domain.entity.ArticleEntity
import com.example.urssu.domain.entity.UserEntity
import javax.validation.constraints.NotBlank

data class ArticleReqDto (
    val email: String,

    val password: String,

    @field: NotBlank
    val title: String,

    @field: NotBlank
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
