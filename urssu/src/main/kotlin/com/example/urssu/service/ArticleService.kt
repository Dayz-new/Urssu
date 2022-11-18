package com.example.urssu.service

import com.example.urssu.config.BaseException
import com.example.urssu.config.BaseResponseStatus
import com.example.urssu.config.resolver.AuthInfo
import com.example.urssu.domain.entity.ArticleEntity
import com.example.urssu.domain.entity.CommentEntity
import com.example.urssu.domain.entity.UserEntity
import com.example.urssu.domain.repository.ArticleRepository
import com.example.urssu.domain.repository.CommentRepository
import com.example.urssu.domain.repository.UserRepository
import com.example.urssu.dto.article.ArticleReqDto
import com.example.urssu.dto.user.UserInfoDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ArticleService {

    @Autowired lateinit var articleRepository: ArticleRepository

    @Autowired lateinit var commentRepository: CommentRepository

    @Autowired lateinit var userRepository: UserRepository


    fun postArticle(articleReqDto: ArticleReqDto, authInfoDto: AuthInfo): ArticleEntity{
        if(userRepository.findByEmail(authInfoDto.email).isEmpty) {
            val baseException = BaseException(BaseResponseStatus.USER_EMPTY_USER)
            throw baseException
        }

        val userEntity: UserEntity = userRepository.findByEmail(authInfoDto.email).get()
        return articleRepository.save(articleReqDto.toEntity(userEntity))
    }

    fun updateArticle(articleReqDto: ArticleReqDto, authInfoDto: AuthInfo, articleId: Int): ArticleEntity{
        if(userRepository.findByEmail(authInfoDto.email).isEmpty) {
            val baseException = BaseException(BaseResponseStatus.USER_EMPTY_USER)
            throw baseException
        }

        var articleEntity: ArticleEntity =  articleRepository.findById(articleId).get()
        val userEntity: UserEntity = userRepository.findByEmail(authInfoDto.email).get()
        articleEntity.updateEntity(articleReqDto,userEntity)
        return articleRepository.save(articleEntity)
    }

    fun deleteArticle(userInfoDto: UserInfoDto, articleId: Int){
        if(userRepository.findByEmail(userInfoDto.email).isEmpty) {
            val baseException = BaseException(BaseResponseStatus.USER_EMPTY_USER)
            throw baseException
        }
        val articles: List<ArticleEntity> = articleRepository.findAllByEmailAndPassword(userInfoDto.email, userInfoDto.password)
        for(article in articles){
            if(article.articleId == articleId) {
                val comments: List<CommentEntity> = commentRepository.findAllByArticleId(article.articleId)
                for (comment in comments) {
                    commentRepository.delete(comment)
                }
                articleRepository.delete(article)
            }
        }
    }
}