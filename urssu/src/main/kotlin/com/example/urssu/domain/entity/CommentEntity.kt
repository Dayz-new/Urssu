package com.example.urssu.domain.entity

import com.example.urssu.dto.comment.CommentReqDto
import com.example.urssu.dto.comment.CommentResDto
import javax.persistence.*

@Table(name = "comment")
@Entity
data class CommentEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    var commentId: Int = 0,

    @Column(nullable = false)
    var content: String,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id")
    var articleEntity: ArticleEntity,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    var userEntity: UserEntity
) : BaseTimeEntity()
{
    fun toCommentResDto(): CommentResDto {
        return CommentResDto(
            commentId = commentId,
            email = userEntity.email,
            content = content
        )
    }

    fun updateEntity(commentReqDto: CommentReqDto, userEntity: UserEntity, articleEntity: ArticleEntity){
        content = commentReqDto.content
        this.userEntity = userEntity
        this. articleEntity = articleEntity
    }
}