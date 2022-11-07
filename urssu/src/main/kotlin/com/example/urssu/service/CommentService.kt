package com.example.urssu.service

import com.example.urssu.config.BaseException
import com.example.urssu.config.BaseResponseStatus
import com.example.urssu.domain.entity.ArticleEntity
import com.example.urssu.domain.entity.CommentEntity
import com.example.urssu.domain.entity.UserEntity
import com.example.urssu.domain.repository.ArticleRepository
import com.example.urssu.domain.repository.CommentRepository
import com.example.urssu.domain.repository.UserRepository
import com.example.urssu.dto.comment.CommentReqDto
import com.example.urssu.dto.user.UserInfoDto
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
        if(userRepository.findByEmail(commentReqDto.email).isEmpty) {
            val baseException = BaseException(BaseResponseStatus.USER_EMPTY_USER)
            throw baseException
        }

        if(articleRepository.findById(articleId).isEmpty){
            val baseException = BaseException(BaseResponseStatus.POST_NOT_EXIST_POST)
            throw baseException
        }

        val userEntity: UserEntity = userRepository.findByEmail(commentReqDto.email).get()
        val articleEntity: ArticleEntity = articleRepository.findById(articleId).get()
        return commentRepository.save(commentReqDto.toEntity(userEntity, articleEntity))
    }

    fun updateComment(commentReqDto: CommentReqDto, articleId: Int, commentId: Int): CommentEntity{
        if(userRepository.findByEmail(commentReqDto.email).isEmpty) {
            val baseException = BaseException(BaseResponseStatus.USER_EMPTY_USER)
            throw baseException
        }

        var commentEntity: CommentEntity =  commentRepository.findById(commentId).get()
        val userEntity: UserEntity = userRepository.findByEmail(commentReqDto.email).get()
        val articleEntity: ArticleEntity = articleRepository.findById(articleId).get()

        commentEntity.updateEntity(commentReqDto, userEntity, articleEntity)
        return commentRepository.save(commentEntity)
    }

    fun deleteComment(userInfoDto: UserInfoDto, articleId: Int, commentId: Int){
        if(userRepository.findByEmail(userInfoDto.email).isEmpty) {
            val baseException = BaseException(BaseResponseStatus.USER_EMPTY_USER)
            throw baseException
        }

        val comments: List<CommentEntity> = commentRepository.findAllByEmailAndPassword(userInfoDto.email, userInfoDto.password)
        for(comment in comments){
            if(comment.articleEntity.articleId == articleId && comment.commentId == commentId)
                commentRepository.delete(comment)
        }
    }
}