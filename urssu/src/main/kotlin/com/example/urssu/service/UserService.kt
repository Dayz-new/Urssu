package com.example.urssu.service

import com.example.urssu.config.BaseException
import com.example.urssu.config.BaseResponseStatus
import com.example.urssu.domain.entity.ArticleEntity
import com.example.urssu.domain.entity.CommentEntity
import com.example.urssu.domain.entity.UserEntity
import com.example.urssu.domain.repository.ArticleRepository
import com.example.urssu.domain.repository.CommentRepository
import com.example.urssu.domain.repository.UserRepository
import com.example.urssu.dto.user.JoinReqUserDto
import com.example.urssu.dto.user.UserInfoDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Transactional
@Service
class UserService{
    @Autowired lateinit var userRepository: UserRepository

    @Autowired lateinit var articleRepository: ArticleRepository

    @Autowired lateinit var commentRepository: CommentRepository

    @Autowired lateinit var articleService: ArticleService

    fun signUp(joinReqUserDto: JoinReqUserDto): UserEntity {
        if(userRepository.findByEmail(joinReqUserDto.email).isPresent){
            val baseException = BaseException(BaseResponseStatus.USER_ALREADY_EXIST_USER)
            throw baseException
        }

        return userRepository.save(joinReqUserDto.toEntity())
    }

    fun deleteUser(userInfoDto: UserInfoDto){
        if(userRepository.findByEmailAndPassword(userInfoDto.email, userInfoDto.password).isEmpty) {
            val baseException = BaseException(BaseResponseStatus.USER_EMPTY_USER)
            throw baseException
        }

        val userEntity: UserEntity = userRepository.findByEmailAndPassword(userInfoDto.email, userInfoDto.password).get()

        val articles: List<ArticleEntity> = articleRepository.findAllByEmailAndPassword(userEntity.email, userEntity.password)
        for(article in articles){
            articleService.deleteArticle(userEntity.toUserInfoDto(), article.articleId)
        }

        val comments: List<CommentEntity?> = commentRepository.findAllByEmailAndPassword(userEntity.email, userEntity.password)
        for(comment in comments){
            commentRepository.delete(comment)
        }

        userRepository.delete(userEntity)

    }
}