package com.example.urssu.service

import com.example.urssu.domain.entity.ArticleEntity
import com.example.urssu.domain.entity.CommentEntity
import com.example.urssu.domain.entity.UserEntity
import com.example.urssu.domain.repository.ArticleRepository
import com.example.urssu.domain.repository.CommentRepository
import com.example.urssu.domain.repository.UserRepository
import com.example.urssu.dto.ArticleReqDto
import com.example.urssu.dto.CommentReqDto
import com.example.urssu.dto.UserInfoDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CommentService {

    @Autowired lateinit var userRepository: UserRepository

    @Autowired lateinit var articleRepository: ArticleRepository

    @Autowired lateinit var commentRepository: CommentRepository

    fun postComment(commentReqDto: CommentReqDto, articleId: Int): CommentEntity {
        val userEntity: UserEntity = userRepository.findByEmail(commentReqDto.email)
        val articleEntity: ArticleEntity = articleRepository.findById(articleId).get()
        return commentRepository.save(commentReqDto.toEntity(userEntity, articleEntity))
    }

    fun updateComment(commentReqDto: CommentReqDto, articleId: Int, commentId: Int): CommentEntity{
        var commentEntity: CommentEntity =  commentRepository.findById(commentId).get()
        commentEntity = updateCommentUsingDto(commentEntity, commentReqDto, articleId)
        return commentRepository.save(commentEntity)
    }

    fun deleteComment(userInfoDto: UserInfoDto, articleId: Int, commentId: Int){
        val comments: List<CommentEntity> = commentRepository.findAllByEmailAndPassword(userInfoDto.email, userInfoDto.password)
        for(comment in comments){
            if(comment.articleEntity.articleId == articleId && comment.commentId == commentId)
                commentRepository.delete(comment)
        }
    }

    private fun updateCommentUsingDto(commentEntity: CommentEntity, commentReqDto: CommentReqDto, articleId: Int): CommentEntity{
        val userEntity: UserEntity = userRepository.findByEmailAndPassword(commentReqDto.email, commentReqDto.password)
        val articleEntity: ArticleEntity = articleRepository.findById(articleId).get()
        commentEntity.content = commentReqDto.content
        commentEntity.articleEntity = articleEntity
        commentEntity.userEntity = userEntity
        return commentEntity
    }
}