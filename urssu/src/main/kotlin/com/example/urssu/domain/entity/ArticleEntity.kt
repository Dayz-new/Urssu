package com.example.urssu.domain.entity

import com.example.urssu.dto.ArticleReqDto
import com.example.urssu.dto.ArticleResDto
import javax.persistence.*

@Table(name = "article")
@Entity
data class ArticleEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    var articleId: Int = 0,

    var content: String,

    var title: String,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    var userEntity: UserEntity

) : BaseTimeEntity()
{
    fun toArticleResDto(): ArticleResDto{
        return ArticleResDto(
            articleId = articleId,
            email = userEntity.email,
            title = title,
            content = content
        )
    }

    fun updateEntity(articleReqDto: ArticleReqDto, userEntity: UserEntity){
        title = articleReqDto.title
        content = articleReqDto.content
        this.userEntity = userEntity
    }
}