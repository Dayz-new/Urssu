package com.example.urssu.service

import com.example.urssu.config.BaseException
import com.example.urssu.config.BaseResponseStatus
import com.example.urssu.config.resolver.AuthInfo
import com.example.urssu.domain.entity.ArticleEntity
import com.example.urssu.domain.entity.CommentEntity
import com.example.urssu.domain.entity.UserEntity
import com.example.urssu.domain.repository.ArticleRepository
import com.example.urssu.domain.repository.CommentRepository
import com.example.urssu.domain.repository.user.UserRepository
import com.example.urssu.dto.comment.CommentReqDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CommentService {

    @Autowired lateinit var userRepository: UserRepository

    @Autowired lateinit var articleRepository: ArticleRepository

    @Autowired lateinit var commentRepository: CommentRepository

    fun postComment(commentReqDto: CommentReqDto, articleId: Int, authInfo: AuthInfo): CommentEntity {
        if(userRepository.findByEmail(authInfo.email).isEmpty) {
            val baseException = BaseException(BaseResponseStatus.USER_EMPTY_USER)
            throw baseException
        }

        if(articleRepository.findById(articleId).isEmpty){
            val baseException = BaseException(BaseResponseStatus.POST_NOT_EXIST_POST)
            throw baseException
        }

        val userEntity: UserEntity = userRepository.findByEmail(authInfo.email).get()
        val articleEntity: ArticleEntity = articleRepository.findById(articleId).get()
        return commentRepository.save(commentReqDto.toEntity(userEntity, articleEntity))
    }

    fun updateComment(commentReqDto: CommentReqDto, articleId: Int, commentId: Int, authInfo: AuthInfo): CommentEntity{
        if(userRepository.findByEmail(authInfo.email).isEmpty) {
            val baseException = BaseException(BaseResponseStatus.USER_EMPTY_USER)
            throw baseException
        }

        var commentEntity: CommentEntity =  commentRepository.findById(commentId).get()
        val userEntity: UserEntity = userRepository.findByEmail(authInfo.email).get()
        val articleEntity: ArticleEntity = articleRepository.findById(articleId).get()

        commentEntity.updateEntity(commentReqDto, userEntity, articleEntity)
        return commentRepository.save(commentEntity)
    }

    fun deleteComment(articleId: Int, commentId: Int, authInfo: AuthInfo){
        if(userRepository.findByEmail(authInfo.email).isEmpty) {
            val baseException = BaseException(BaseResponseStatus.USER_EMPTY_USER)
            throw baseException
        }

        val comments: List<CommentEntity> = commentRepository.findAllByEmail(authInfo.email)
        for(comment in comments){
            if(comment.articleEntity.articleId == articleId && comment.commentId == commentId)
                commentRepository.delete(comment)
        }
    }
}